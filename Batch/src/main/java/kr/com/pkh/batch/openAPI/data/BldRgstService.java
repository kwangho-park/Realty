package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.util.HTTPrequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.text.Bidi;
import java.util.Map;

/**
 *  provider : 공공데이터 포털
 *  service name : 국토교통부 건축물대장정보 서비스
 *  description : 건축물대장에서 관리하고 있는 총괄표제부, 표제부, 층별개요, 부속지번, 전유공용면적,
 *  오수정화시설, 주택가격, 전유부, 지역지구구역 등 속성정보(대용량 원시DB)
 */
@Slf4j
@Component
public class BldRgstService {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    public BldRgstService(){}

    public BldRgstService(String apiKey){
        this.apiKey = apiKey;
    }

    private String serviceDomain="http://apis.data.go.kr/1613000/BldRgstService_v2";

    /**
     * 건축물대장 전유/공유 면적 조회 (동,호수 단위) <br>
     *
     * @param numOfRows 페이지당 리스트 수
     * @param pageNo 페이지 번호
     * @param sigunguCd 시군구코드_행정표준코드(5자리 정수) (필수)
     * @param bjdongCd 법정정동코드_행정표준코드(5자리 정수) (필수)
     * @param platGbCd 대지구분코드 (0: 대지, 1: 산, 2: 블록)
     * @param bun 번
     * @param ji 지
     * @param dongNm 동명칭
     * @param hoNm 호명칭
     * @param startDate 검색시작일 (YYYYMMDD)
     * @param endDate 검색종료일 (YYYYMMDD)
     *
     */
    public String getBrExposPubuseAreaInfo(String numOfRows, String pageNo, String sigunguCd, String bjdongCd, String platGbCd,
                                         String bun, String ji, String dongNm, String hoNm, String startDate, String endDate){


        String responseXML="";

        try{

            String path = "/getBrExposPubuseAreaInfo";

            Map<String, String> parameters = Map.of(
                    "serviceKey", this.apiKey,
                        "numOfRows",numOfRows,
                        "pageNo",pageNo,
                    "sigunguCd",sigunguCd,
                    "bjdongCd",bjdongCd,
                    "platGbCd",platGbCd,
                    "bun",bun,
                    "ji",ji,
                    "dongNm",dongNm,
                    "hoNm",hoNm
//                    "startDate",startDate
            );

//            parameters.put("endDate", endDate);


            String responseXml = HTTPrequest.responseXML(serviceDomain, path, parameters);

        }catch(Exception e){
            e.printStackTrace();

        }finally{
            return responseXML;
        }
    }

}
