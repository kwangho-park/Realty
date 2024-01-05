package kr.com.pkh.batch.openAPI.vworld;


import kr.com.pkh.batch.util.HTTPrequest;
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
     * @throws Exception
     */
    public void getBuildingUse(String pnu, String mainPrposCode,String detailPrposCode , String format,
                               String numOfRows, String pageNo, String key, String domain) throws Exception {

        String path = "/ned/data/getBuildingUse";

        Map<String, String> parameters = Map.of(
                "key", URLEncoder.encode(key,"UTF-8"),         // 필수
//                "mainPrposCode",mainPrposCode,                        // 옵션
//                "detailPrposCode",detailPrposCode,                    // 옵션
                "format",format,                                      // 옵션
//                "numOfRows",numOfRows,                                // 옵션
//                "pageNo",pageNo                                       // 옵션
                "pnu",URLEncoder.encode(pnu,"UTF-8")               // 필수
//                "domain",domain                                       // 옵션
        );

        //HTTPrequest.responseXML(serviceDomain, "",path, "",parameters);

        HTTPrequest.responseJSON(serviceDomain, path,parameters);

    }



}
