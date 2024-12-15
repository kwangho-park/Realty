package kr.com.pkh.batch.openAPI.naver.service;


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
 * 공급자 : naver cloud
 *
 * 인증인가 방식 : API key
 * http method : GET 방식만 지원
 *
 * domain : https://naveropenapi.apigw.ntruss.com
 *
 */
@Slf4j
@Component
public class GeoCoder {

    private String serviceDomain="https://naveropenapi.apigw.ntruss.com";

    @Value("${naver.map.api.key.id}")
    private String keyId;
    @Value("${naver.map.api.key}")
    private String key;

    public JSONObject getGps(String address) throws Exception {

        String path = "map-geocode/v2/geocode";

        Map<String, String> parameters = Map.of(
                "query" , address           // 필수
        );

        Map<String, String> headerMap = Map.of(
                 "x-ncp-apigw-api-key-id" , this.keyId
                ,"x-ncp-apigw-api-key" , this.key
        );

       return  HTTPrequest.responseJSON(serviceDomain, path,parameters,headerMap);

    }

    // [review] 테스트 코드에서 테스트 할 수 있도록 함수구조를 작성해야할것으로 판단됨 (ex) key 를 parameter로 전달, 오버로드된 메소드로 대체 고려 등)
    public JSONObject getBuildingUse(String pnu) {

        JSONObject jsonObject = new JSONObject();

        try {

            String path = "/ned/data/getBuildingUse";

            Map<String, String> parameters = Map.of(
                    "pnu",URLEncoder.encode(pnu,"UTF-8"),           // '필수'
//                "mainPrposCode",mainPrposCode,                            // 옵션
//                "detailPrposCode",detailPrposCode,                        // 옵션
                    "format","json",                                        // 옵션
                    "numOfRows","1",                                        // 옵션
//                "pageNo",pageNo                                           // 옵션
                    "key", URLEncoder.encode("ADFE2B57-DC15-3072-9932-02BD45403FCA","UTF-8"),              // '필수'  [review] 임시로 하드코딩
                    "domain","www.realty.co.kr"                             // 옵션 (중요: 인증키 발급 시 설정한 도메인과 동일해야함)
            );

            jsonObject = HTTPrequest.responseJSON(serviceDomain, path,parameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
