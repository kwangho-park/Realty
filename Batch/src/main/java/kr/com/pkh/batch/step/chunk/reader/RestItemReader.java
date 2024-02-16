package kr.com.pkh.batch.step.chunk.reader;


import kr.com.pkh.batch.dto.TradeDTO;
import kr.com.pkh.batch.openAPI.data.RTMSOBJSvc;
import kr.com.pkh.batch.singleton.Scope;
import kr.com.pkh.batch.util.DateUtil;
import kr.com.pkh.batch.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.YearMonth;


@Slf4j
@Component
public class RestItemReader implements ItemReader<TradeDTO> {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    @Value("${collectRealtyJob.mode}")
    private String mode;

    @Value("${collectRealtyJob.mode.init.startDate}")
    private String startDate;       // format : YYYYMM

    @Value("${collectRealtyJob.mode.init.endDate}")
    private String endDate;         // format : YYYYMM

    private boolean dataRead = false;


    private RTMSOBJSvc RTMSOBJSvc;

    public RestItemReader(RTMSOBJSvc RTMSOBJSvc) {
        this.RTMSOBJSvc = RTMSOBJSvc;
    }

    @Override
    public TradeDTO read() {

        TradeDTO tradeDTO = new TradeDTO();

        String LAWD_CD="41192";     // 부천시  (DB 조회예정)
        String DEAL_YMD="";

        Scope scope = Scope.getInstance();

        try{

            // validation in properties //
            // (예정) 모드 유효성 확인

            if(mode.toLowerCase().equals("init")){
                // validation in properties //
                // (예정) 기간 형식 및 유효성 확인

                // properties date 데이터 설정
                if(scope.isScopeFlag()){
                    scope.setStartDate(DateUtil.stringToYearMonth(startDate));
                    scope.setEndDate(DateUtil.stringToYearMonth(endDate));
                    scope.setScopeFlag(false);
                }
            }



            // 운영 모드 //
            if(mode.toLowerCase().equals("op")){


                // 전체 페이지 조회 시 reader 종료
                if(!(scope.getPageNo() <= scope.getTotalPage()+1)){
                    log.info("[collectRealtyJob] 부동산 매매 거래 데이터 수집완료 ");
                    return null;
                }

                log.info("[collectRealtyJob] reader operation mode");


                // get deal ymd //
                LocalDate date = LocalDate.now();
                String today = StringUtil.removeAllCharsFromString(date.toString(),'-');
                DEAL_YMD = today.substring(0,today.length()-2);      // 일 데이터 제거

                log.info("DEAL_YMD (today month) : "+DEAL_YMD);

                tradeDTO = RTMSOBJSvc.getRTMSDataSvcAptTradeDev(apiKey,
                        String.valueOf(scope.getPageNo()),
                        String.valueOf(scope.getNumOfRows()),
                        LAWD_CD,
                        DEAL_YMD);

                // 페이지 정보 업데이트 (pageNo, totalPage)
                scope.incrementPageNo();
                scope.updateTotalPage(Integer.parseInt(tradeDTO.getPageDTO().getTotalCount()), scope.getNumOfRows() );


                // 초기화 모드 //
            }else if(mode.toLowerCase().equals("init")){



                YearMonth startDate = scope.getStartDate();
                YearMonth endDate = scope.getEndDate();

                // startDate 가 endDate 이후 날짜인 경우 reader 종료
                if(startDate.compareTo(endDate) > 0){
                    log.info("[collectRealtyJob] 초기화를 위한 부동산 매매 거래 데이터 수집완료 ");
                    log.info("[collectRealtyJob] start date :"+ DateUtil.yearMonthToString(startDate));
                    log.info("[collectRealtyJob] end date :"+ DateUtil.yearMonthToString(endDate));

                    return null;
                }

                // 수집 실행
                log.info("[collectRealtyJob] reader operation mode");

                tradeDTO = RTMSOBJSvc.getRTMSDataSvcAptTradeDev(apiKey,
                        String.valueOf(scope.getPageNo()),
                        String.valueOf(scope.getNumOfRows()),
                        LAWD_CD,
                        DateUtil.yearMonthToString(scope.getStartDate()) );

                // scope //
                // 페이지 정보 업데이트 (pageNo, totalPage)
                scope.incrementPageNo();
                scope.updateTotalPage(Integer.parseInt(tradeDTO.getPageDTO().getTotalCount()), scope.getNumOfRows() );

                // 전체 페이지 조회 시 date 업데이트 (startDate)
                if(!(scope.getPageNo() <= scope.getTotalPage()+1)){
                    // start date 증가
                    scope.incrementStartDate();

                    // pageNo, totalPage 초기화
                    scope.initPageNo();
                    scope.initTotalPage();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return tradeDTO;
    }
}