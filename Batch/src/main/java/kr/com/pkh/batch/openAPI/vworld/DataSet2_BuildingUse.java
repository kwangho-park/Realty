package kr.com.pkh.batch.openAPI.vworld;


import kr.com.pkh.batch.util.HTTPrequest;
import org.json.JSONObject;
import kr.com.pkh.batch.util.json.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 공급자 : v world
 * dataSetSeq : 2
 * 조회가능 데이터 : 용도별 건물정보 데이터셋 (건물속성정보, WMS, WFS)
 *
 * 참고 : 공공데이터 포털의  'BuildingUseService' 대체
 * 인증인가 방식 : API key
 * http method : GET 방식만 지원
 *
 * domain : https://api.vworld.kr
 *
 */
@Slf4j
@Component
public class DataSet2_BuildingUse {

    private String serviceDomain="https://api.vworld.kr";

    @Value("${vworld.openApi.apikey}")
    private String key;
    /**
     * 필지고유정보(pnu), 용도 정보를 통해 건물속성 조회
     *
     * @param pnu 필지 고유번호 (시도[2]+시군구[3]+읍면동[3]) = 특정 필지 검색
     * @param mainPrposCode 주요용도코드
     * @param detailPrposCode  세부용도코드
     * @param format 응답결과 형식(xml or json)
     * @param numOfRows 검색건수
     * @param pageNo 페이지 번호
     * @param key 인증키 (개발키 or 운영키)
     * @param domain API KEY를 발급받을때 입력했던 URL
     * @return (예정) 건물명, gis 건물통합식별번호, pnu(고유번호) , 용적율 등
     * @throws Exception
     */
    public void getBuildingUse(String pnu, String mainPrposCode,String detailPrposCode , String format,
                               String numOfRows, String pageNo, String key, String domain) throws Exception {

        String path = "/ned/data/getBuildingUse";

        Map<String, String> parameters = Map.of(
                "pnu",URLEncoder.encode(pnu,"UTF-8"),           // 필수
//                "mainPrposCode",mainPrposCode,                        // 옵션
//                "detailPrposCode",detailPrposCode,                    // 옵션
                "format",format,                                        // 옵션
                 "numOfRows",numOfRows,                                // 옵션
//                "pageNo",pageNo                                       // 옵션
                "key", URLEncoder.encode(key,"UTF-8")       ,        // 필수
                "domain",domain                                       // 옵션
        );

        HTTPrequest.responseJSON(serviceDomain, path,parameters);

    }


    /**
     * 좌표등의 위치정보를 통해 지정한 범위내의 용도별 건물 WFS 조회 (vector data for map)
     *
     * @param typename 피처 유형 (요청 대상인 피처유형이름??)
     * @param bbox 검색범위 (lc1,lc2,uc1,uc2,좌표체계) = 좌표로 검색
     *             (EPSG:4326 경우 좌측하단(y,x), 우측상단(y,x) => ymin,xmin,ymax,xmax)
     *
     * @param pnu 필지 고유번호 (시도[2]+시군구[3]+읍면동[3]) = 특정 필지 검색
     * @param maxFeatures 피처의 최대값 (max 100)
     * @param resultType 응답형태 (?)
     * @param srsName GPS 좌표체계 (EPSG:4326 등)
     * @param key api key
     * @param domain API KEY를 발급받을때 입력했던 URL
     *
     * @return (예정)
     * @throws Exception
     */
    public void getBuildingUseWFS(String typename, String bbox, String pnu, String maxFeatures,
                                  String resultType, String srsName, String key, String domain)
            throws Exception {

        String servicePort = "";
        String commonPath = "";
        String path ="/ned/wfs/getBuildingUseWFS";


        Map<String, String> parameters = Map.of(

//                "typename",typename,            // 옵션
                "bbox", bbox,                   // 옵션
//                "pnu", pnu,                   // 옵션
//                "maxFeatures",maxFeatures,    // 옵션
//                "resultType",resultType,      // 옵션
                "srsName",srsName,              // 옵션 (테스트 결과 필수값으로 확인됨)
                "key",key                       // 필수
//                "domain",domain               // 옵션

        );

        HTTPrequest.responseXML( serviceDomain,  servicePort,  commonPath,  path, parameters);
    }

    public JSONObject getBuildingUse(String pnu) {

        JSONObject jsonObject = new JSONObject();
        try {

            String path = "/ned/data/getBuildingUse";

            Map<String, String> parameters = Map.of(
                    "pnu",URLEncoder.encode(pnu,"UTF-8"),           // 필수
//                "mainPrposCode",mainPrposCode,                        // 옵션
//                "detailPrposCode",detailPrposCode,                    // 옵션
                    "format","json",                                        // 옵션
                "numOfRows","1",                                // 옵션
//                "pageNo",pageNo                                       // 옵션
                    "key", URLEncoder.encode(key,"UTF-8") ,              // 필수
                                    "domain","www.realty.co.kr"                                       // 옵션
            );

           jsonObject = HTTPrequest.responseJSON(serviceDomain, path,parameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }




}
