package kr.com.pkh.batch.step.chunk.reader;


import kr.com.pkh.batch.code.CustomErrorCode;
import kr.com.pkh.batch.dao.RegionCodeRepository;
import kr.com.pkh.batch.dto.RegionCodeEntity;
import kr.com.pkh.batch.dto.TradeDTO;
import kr.com.pkh.batch.exception.CustomException;
import kr.com.pkh.batch.openAPI.data.RTMSOBJSvc;
import kr.com.pkh.batch.singleton.Scope;
import kr.com.pkh.batch.util.DateUtil;
import kr.com.pkh.batch.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.com.pkh.batch.code.CustomErrorCode.DATE_FLAG;
import static kr.com.pkh.batch.code.CustomErrorCode.REGION_FLAG;


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

    private RTMSOBJSvc RTMSOBJSvc;

    private RegionCodeRepository regionCodeRepository;

    @Autowired
    public RestItemReader(RTMSOBJSvc RTMSOBJSvc, RegionCodeRepository regionCodeRepository) {
        this.RTMSOBJSvc = RTMSOBJSvc;
        this.regionCodeRepository=regionCodeRepository;
    }

    /**
     * 수집 기간과 지역코드값에 따라 매매 거래정보 수집 <br>
     *
     * @return
     */
    @Override
    public TradeDTO read() {

        TradeDTO tradeDTO = new TradeDTO();

        String lawdCd="";
        String numOfRows="";        // (예정) 공공데이터 포탈 일 최대 트래픽 1,000건으로 인해 row 설정가능하도록 변경예정 (10-> row MAX)

        String dealYmd="";

        Scope scope = Scope.getInstance();

        try{

            // validation in properties //
            // (예정) 모드 유효성 확인
            // (예정) 기간 형식 및 유효성 확인
            if(scope.isScopeFlag()){
                
                this.initScope();
            }


            // 운영 모드 //
            if(mode.toLowerCase().equals("op")){


                log.info("region id : "+scope.getRegionId());
                log.info("region size : "+scope.regionCodeSize());

                // reader 동작 제어 //
                // reader 종료 시점 : 전체 페이지 수집 -> 전체 지역코드 순환
                if(scope.regionCodeSize() < (scope.getRegionId() )){
                    log.info("[read] 부동산 매매 거래 데이터 수집완료 ");
                    return null;
                }

                // reader 반복 종료 시점 : 페이지 전체 순환
                if(scope.getTotalPage() < scope.getPageNo() && !(scope.getTotalPage()==0) ){

                    // pageNo, totalPage 초기화
                    scope.initPageNo();
                    scope.initTotalPage();

                    // 지역코드 업데이트
                    scope.incrementRegionId();
                    throw new CustomException(REGION_FLAG);
                }


                log.info("[read] reader operation mode");


                // 매매 거래정보 수집 //
                log.info("region id : "+scope.getRegionId());
                log.info("region size : "+scope.regionCodeSize());

                lawdCd = String.valueOf( scope.getRegionCodeList().get(scope.getRegionId()-1 ) );
                log.info("lawdCd : "+ lawdCd);

                LocalDate date = LocalDate.now();
                String today = StringUtil.removeAllCharsFromString(date.toString(),'-');
                dealYmd = today.substring(0,today.length()-2);      // 일 데이터 제거

                log.info("DEAL_YMD (today month) : "+dealYmd);

                tradeDTO = RTMSOBJSvc.getRTMSDataSvcAptTradeDev(apiKey,
                        String.valueOf(scope.getPageNo()),
                        String.valueOf(scope.getNumOfRows()),
                        lawdCd,
                        dealYmd);

                // 수집 페이지 범위 업데이트 //
                scope.incrementPageNo();
                scope.updateTotalPage(tradeDTO.getPageDTO().getTotalCount(), scope.getNumOfRows() );


                log.info("scope page no : "+ scope.getPageNo());
                log.info("scope total page : "+ scope.getTotalPage() );

                // 초기화 모드 //
            }else if(mode.toLowerCase().equals("init")){

                YearMonth startDate = scope.getStartDate();
                YearMonth endDate = scope.getEndDate();

                // reader 동작 제어 //
                // reader 종료 시점 : 전체 페이지 수집 -> 설정된 수집기간 순환 -> 전체 지역코드 순환
                if(scope.regionCodeSize() < (scope.getRegionId() )){
                    log.info("[read] 초기화를 위한 부동산 매매 거래 데이터 수집완료 ");
                    return null;
                }

                // reader 반복 종료 시점 : 전체 기간 순환
                if(startDate.compareTo(endDate) > 0){
                    log.info("[read] 초기화를 위한 부동산 매매 거래 데이터 수집완료 ");
                    log.info("[read] start date :"+ DateUtil.yearMonthToString(startDate));
                    log.info("[read] end date :"+ DateUtil.yearMonthToString(endDate));

                    // pageNo, totalPage, 수집 날짜 초기화
                    scope.initPageNo();
                    scope.initTotalPage();
                    scope.setStartDate(DateUtil.stringToYearMonth(this.startDate));


                    // 지역코드 업데이트
                    scope.incrementRegionId();

                    throw new CustomException(REGION_FLAG);
                }


                // reader 반복 종료 시점 : 페이지 전체 순환
                if(scope.getTotalPage() < scope.getPageNo() && !(scope.getTotalPage()==0) ){

                    // pageNo, totalPage 초기화
                    scope.initPageNo();
                    scope.initTotalPage();

                    // 수집 날짜 업데이트
                    scope.incrementStartDate();
                    throw new CustomException(DATE_FLAG);
                }


                // 매매 거래 정보 수집 //
                log.info("[read] reader operation mode");

                // 매매 거래정보 수집 //
                log.info("region id : "+scope.getRegionId());
                log.info("region size : "+scope.regionCodeSize());

                lawdCd = String.valueOf( scope.getRegionCodeList().get(scope.getRegionId()-1) );
                log.info("lawdCd : "+ lawdCd);

                log.info("DEAL_YMD (start date) : "+DateUtil.yearMonthToString(scope.getStartDate()));

                tradeDTO = RTMSOBJSvc.getRTMSDataSvcAptTradeDev(apiKey,
                        String.valueOf(scope.getPageNo()),
                        String.valueOf(scope.getNumOfRows()),
                        lawdCd,
                        DateUtil.yearMonthToString(scope.getStartDate()) );


                // 수집 페이지 범위 설정 //
                scope.incrementPageNo();
                scope.updateTotalPage(tradeDTO.getPageDTO().getTotalCount(), scope.getNumOfRows() );

            }

        }catch(CustomException e){

            // CustomErrorCode 종류에 따라 Region id 의 차이가 있음으로 분기처리 //
            if(e.getCustomErrorCode() == DATE_FLAG){
                log.info("[read] "+e.getDetailMessage() +" = custom error code : "+e.getCustomErrorCode()
                        +" / 지역코드 : "+scope.getRegionCodeList().get(scope.getRegionId()-1 )
                        +" / batch mode : "+mode+")");

            }else if(e.getCustomErrorCode() == REGION_FLAG){
                log.info("[read] "+e.getDetailMessage() +" = custom error code : "+e.getCustomErrorCode()
                        +" / 지역코드 : "+scope.getRegionCodeList().get(scope.getRegionId()-2 )
                        +" / batch mode : "+mode+")");
            }


        } catch(NullPointerException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }

        return tradeDTO;
    }

    // 매매거래 데이터 수집을 위한 초기 scope 설정
    public void initScope(){

        Scope scope = Scope.getInstance();
        List<Integer> regionCodeList = new ArrayList<Integer>();

        // batch mode 별 scope 설정 //
        if(mode.toLowerCase().equals("op")){
            scope.setScopeFlag(false);
            scope.setPageNo(1);
            scope.setNumOfRows(10);
            scope.setTotalPage(0);


        } else if(mode.toLowerCase().equals("init")){

            scope.setScopeFlag(false);
            scope.setPageNo(1);
            scope.setNumOfRows(10);
            scope.setTotalPage(0);
            scope.setStartDate(DateUtil.stringToYearMonth(startDate));
            scope.setEndDate(DateUtil.stringToYearMonth(endDate));
        }

        // 지역코드 설정 //
        List<RegionCodeEntity> regionList = regionCodeRepository.findAllByOrderByIdAsc();

        for(RegionCodeEntity item: regionList){
            Long fullCode = item.getRegionCode();       // 앞의 5자리 자르기
            String fullCodeStr = fullCode.toString();
            String codeStr = fullCodeStr.substring(0, Math.max(0, fullCodeStr.length() - 5));

            regionCodeList.add(Integer.parseInt(codeStr));
        }

        scope.setRegionCodeList(regionCodeList);

    }
}