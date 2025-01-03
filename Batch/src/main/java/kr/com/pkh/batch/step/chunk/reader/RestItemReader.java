package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dao.RegionCodeDAO;
import kr.com.pkh.batch.dto.db.RegionCodeDTO;
import kr.com.pkh.batch.dto.api.TradePageDTO;
import kr.com.pkh.batch.exception.CustomException;
import kr.com.pkh.batch.openAPI.data.service.RTMSDataSvc;
import kr.com.pkh.batch.singleton.AptTradeScope;
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
import java.util.List;

import static kr.com.pkh.batch.code.CustomErrorCode.DATE_FLAG;
import static kr.com.pkh.batch.code.CustomErrorCode.REGION_FLAG;

/**
 * 0. 공통
 * - 수도권의 아파트 매매정보는 공공데이터 포탈의 openAPI로 지역코드와 날짜(월) 을 기준으로 조회
 *  (N개의 페이지가 반환 될 수 있음)
 * - 지역코드는 realty DB의 테이블에 저장되어있음
 *
 * 1. 운영 모드 (op)
 * - 배치가 실행되는 월의 수도권 (서울시, 경기도, 인천시) 데이터 조회
 *
 * 2. 초기화 모드 (init)
 * - properties 에 지정한 날짜 구간의 수도권 데이터 조회
 *
 */
@Slf4j
@Component // 해당 reader를 spring bean 으로 등록해 놓음
public class RestItemReader implements ItemReader<TradePageDTO> {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    @Value("${collectRealtyJob.mode}")
    private String mode;

    @Value("${collectRealtyJob.mode.init.startDate}")
    private String startDate;       // format : YYYYMM

    @Value("${collectRealtyJob.mode.init.endDate}")
    private String endDate;         // format : YYYYMM

    private RTMSDataSvc RTMSDataSvc;

    private RegionCodeDAO regionCodeDAO;

    @Autowired
    public RestItemReader(RTMSDataSvc RTMSDataSvc, RegionCodeDAO regionCodeDAO) {
        this.RTMSDataSvc = RTMSDataSvc;
        this.regionCodeDAO=regionCodeDAO;

        String asciiArt=
                        "  _____            _ _             _           _       _       		\n"+
                        " |  __ \\          | | |           | |         | |     | |      		\n"+
                        " | |__) |___  __ _| | |_ _   _    | |__   __ _| |_ ___| |__    		\n"+
                        " |  _  // _ \\/ _` | | __| | | |   | '_ \\ / _` | __/ __| '_ \\   		\n"+
                        " | | \\ \\  __/ (_| | | |_| |_| |   | |_) | (_| | || (__| | | |  		\n"+
                        " |_|  \\_\\___|\\__,_|_|\\__|\\__, |   |_.__/ \\__,_|\\__\\___|_| |_|  \n"+
                        "                          __/ |                                		\n"+
                        "                         |___/                (by Songkwang)       	\n"+
                        "										    	                        ";

        log.info("\n"+asciiArt);


    }

    /**
     * 수집 기간과 지역코드값에 따라 매매 거래정보 수집 <br>
     *
     * @return
     */
    @Override
    public TradePageDTO read() {
        log.info("[read] START");
        TradePageDTO tradePageDTO = new TradePageDTO();

        String lawdCd="";           // 지역코드
        String numOfRows="";        // (예정) 공공데이터 포탈 일 최대 트래픽 1,000건으로 인해 row 설정가능하도록 변경예정 (10-> row MAX)

        String dealYmd="";

        AptTradeScope aptTradeScope = AptTradeScope.getInstance();

        try{

            // validation in properties //
            // (예정) 모드 유효성 확인
            // (예정) 기간 형식 및 유효성 확인
            if(aptTradeScope.isScopeFlag()){
                this.initScope();
            }


            // 운영 모드 //
            if(mode.toLowerCase().equals("op")){


                log.info("region id : "+ aptTradeScope.getRegionSeq());
                log.info("region size : "+ aptTradeScope.getRegionCodeSize());

                // reader 동작 제어 //
                // reader 종료 시점(=job 종료시점) : 전체 페이지 수집 완료 -> 전체 지역코드 순환 완료
                if(aptTradeScope.getRegionCodeSize() < (aptTradeScope.getRegionSeq() )){
                    log.info("[read] 부동산 매매 거래 데이터 수집완료 ");
                    return null;
                }

                // reader 반복 종료 시점 : 전체 xml 페이지 순환 완료
                if(aptTradeScope.getTotalPage() < aptTradeScope.getPageNo() &&
                        !(aptTradeScope.getTotalPage()==0) ){

                    // pageNo, totalPage 초기화
                    aptTradeScope.initPageNo();
                    aptTradeScope.initTotalPage();

                    // 지역코드 업데이트
                    aptTradeScope.incrementRegionSeq();
                    throw new CustomException(REGION_FLAG);
                }

                log.info("----------------------------");
                log.info("[read] reader operation mode");
                log.info("----------------------------");

                // 매매 거래정보 수집 //
                log.info("region id : "+ aptTradeScope.getRegionSeq());
                log.info("region size : "+ aptTradeScope.getRegionCodeSize());

                lawdCd = String.valueOf( aptTradeScope.getRegionCodeList().get(aptTradeScope.getRegionSeq()-1 ) );
                log.info("lawdCd : "+ lawdCd);

                LocalDate date = LocalDate.now();
                String today = StringUtil.removeAllCharsFromString(date.toString(),'-');
                dealYmd = today.substring(0,today.length()-2);      // 일 데이터 제거

                log.info("DEAL_YMD (today month) : "+dealYmd);

                // openAPI 로 데이터 수집 및 PNU 가공
                tradePageDTO = this.RTMSDataSvc.getRTMSDataSvcAptTradeDev(apiKey,
                        String.valueOf(aptTradeScope.getPageNo()),
                        String.valueOf(aptTradeScope.getNumOfRows()),
                        lawdCd,
                        dealYmd);

                // 수집 페이지 범위 업데이트 //
                aptTradeScope.incrementPageNo();
                aptTradeScope.updateTotalPage(tradePageDTO.getPageDTO().getTotalCount(),
                        aptTradeScope.getNumOfRows() );


                log.info("scope page no : "+ aptTradeScope.getPageNo());
                log.info("scope total page : "+ aptTradeScope.getTotalPage() );

                // 초기화 모드 //
            }else if(mode.toLowerCase().equals("init")){

                YearMonth startDate = aptTradeScope.getStartDate();
                YearMonth endDate = aptTradeScope.getEndDate();

                // reader 동작 제어 //
                // reader 종료 시점 : 전체 페이지 수집 -> 설정된 수집기간 순환 -> 전체 지역코드 순환
                if(aptTradeScope.getRegionCodeSize() < (aptTradeScope.getRegionSeq() )){
                    log.info("[read] 초기화를 위한 부동산 매매 거래 데이터 수집완료 ");
                    return null;
                }

                // reader 반복 종료 시점 : 전체 기간 순환
                if(startDate.compareTo(endDate) > 0){
                    log.info("[read] 초기화를 위한 부동산 매매 거래 데이터 수집완료 ");
                    log.info("[read] start date :"+ DateUtil.yearMonthToString(startDate));
                    log.info("[read] end date :"+ DateUtil.yearMonthToString(endDate));

                    // pageNo, totalPage, 수집 날짜 초기화
                    aptTradeScope.initPageNo();
                    aptTradeScope.initTotalPage();
                    aptTradeScope.setStartDate(DateUtil.stringToYearMonth(this.startDate));


                    // 지역코드 업데이트
                    aptTradeScope.incrementRegionSeq();

                    throw new CustomException(REGION_FLAG);
                }


                // reader 반복 종료 시점 : 페이지 전체 순환
                if(aptTradeScope.getTotalPage() < aptTradeScope.getPageNo() && !(aptTradeScope.getTotalPage()==0) ){

                    // pageNo, totalPage 초기화
                    aptTradeScope.initPageNo();
                    aptTradeScope.initTotalPage();

                    // 수집 날짜 업데이트
                    aptTradeScope.incrementStartDate();
                    throw new CustomException(DATE_FLAG);
                }


                // 매매 거래 정보 수집 //
                log.info("----------------------------");
                log.info("[read] reader init mode");
                log.info("----------------------------");

                // 매매 거래정보 수집 //
                log.info("region id : "+ aptTradeScope.getRegionSeq());
                log.info("region size : "+ aptTradeScope.getRegionCodeSize());

                lawdCd = String.valueOf( aptTradeScope.getRegionCodeList().get(aptTradeScope.getRegionSeq()-1) );
                log.info("lawdCd : "+ lawdCd);

                log.info("DEAL_YMD (start date) : "+DateUtil.yearMonthToString(aptTradeScope.getStartDate()));

                // openAPI 로 데이터 수집 및 PNU 가공
                tradePageDTO = this.RTMSDataSvc.getRTMSDataSvcAptTradeDev(apiKey,
                        String.valueOf(aptTradeScope.getPageNo()),
                        String.valueOf(aptTradeScope.getNumOfRows()),
                        lawdCd,
                        DateUtil.yearMonthToString(aptTradeScope.getStartDate()) );


                // 수집 페이지 범위 설정 //
                aptTradeScope.incrementPageNo();
                aptTradeScope.updateTotalPage(tradePageDTO.getPageDTO().getTotalCount(), aptTradeScope.getNumOfRows() );

            }

        }catch(CustomException e){

            // CustomErrorCode 종류에 따라 Region id 의 차이가 있음으로 분기처리 //
            if(e.getCustomErrorCode() == DATE_FLAG){
                log.info("[read] "+e.getDetailMessage() +" = custom code : "+e.getCustomErrorCode()
                        +" / 지역코드 : "+ aptTradeScope.getRegionCodeList().get(aptTradeScope.getRegionSeq()-1 )
                        +" / batch mode : "+mode+")");

            }else if(e.getCustomErrorCode() == REGION_FLAG){
                log.info("[read] "+e.getDetailMessage() +" = custom code : "+e.getCustomErrorCode()
                        +" / 지역코드 : "+ aptTradeScope.getRegionCodeList().get(aptTradeScope.getRegionSeq()-2 )
                        +" / batch mode : "+mode+")");
            }


        } catch(NullPointerException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        log.info("[read] END");
        return tradePageDTO;
    }


    public void initScope() throws Exception {

        AptTradeScope aptTradeScope = AptTradeScope.getInstance();
        List<Integer> regionCodeList = new ArrayList<Integer>();

        // batch mode 별 xml page scope //
        if(mode.toLowerCase().equals("op")){
            aptTradeScope.setScopeFlag(false);
            aptTradeScope.setPageNo(1);
            aptTradeScope.setNumOfRows(10);
            aptTradeScope.setTotalPage(0);


        } else if(mode.toLowerCase().equals("init")){

            aptTradeScope.setScopeFlag(false);
            aptTradeScope.setPageNo(1);
            aptTradeScope.setNumOfRows(10);
            aptTradeScope.setTotalPage(0);
            aptTradeScope.setStartDate(DateUtil.stringToYearMonth(startDate));
            aptTradeScope.setEndDate(DateUtil.stringToYearMonth(endDate));
        }

        // 지역코드 scope  //
           List<RegionCodeDTO> regionList = regionCodeDAO.selectRegionCodeList();

        for(RegionCodeDTO regionCodeDTO: regionList){
            Long fullCode = (long) regionCodeDTO.getRegionCode();
            String fullCodeStr = fullCode.toString();
            String codeStr = fullCodeStr.substring(0, Math.max(0, fullCodeStr.length() - 5));  // 앞의 5자리 자르기

            regionCodeList.add(Integer.parseInt(codeStr));
        }

        aptTradeScope.setRegionCodeList(regionCodeList);

    }

}