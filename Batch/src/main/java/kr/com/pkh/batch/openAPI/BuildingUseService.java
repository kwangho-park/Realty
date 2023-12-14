package kr.com.pkh.batch.openAPI;

import kr.com.pkh.batch.util.HTTPrequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 서비스명 : 용도별 건물 정보 서비스
 *
 * 인증인가 방식 : API key
 * http method : GET 방식만 지원
 *
 * domain : http://apis.data.go.kr
 *
 */
@Slf4j
@Component
public class BuildingUseService {


    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    public BuildingUseService(){}

    public BuildingUseService(String apiKey){this.apiKey = apiKey;}
    private String serviceDomain="http://apis.data.go.kr";
    private String servicePort=null;

    /**
     * 좌표등의 위치정보를 통해 지정한 범위내의 용도별 건물 WFS 조회 (vector data for map)
     *
     * @param typeName 피처 유형 (요청 대상인 피처유형이름??)
     * @param bbox 검색범위 (lc1,lc2,uc1,uc2,좌표체계) = 좌표로 검색
     * @param pnu 필지 고유번호 (시도[2]+시군구[3]+읍면동[3]) = 특정 필지 검색
     * @param maxFeatures 피처의 최대값 (max 100)
     * @param srsName 좌표체계 (?)
     * @param resultType 응답형태  ??
     * @throws Exception
     */
    public void getBuildingUseWFS(String typeName,String bbox, String pnu,
                                  String maxFeatures,String srsName , String resultType) throws  Exception{
        servicePort="";
        String commonPath = "/1611000/nsdi/BuildingUseService";
        String path = "/wfs/getBuildingUseWFS";

        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,        // 필수
//                "typeName", typeName,           // 옵션
//                "bbox", bbox,                   // 옵션
                "pnu",pnu                      // 옵션
//                "maxFeatures",maxFeatures,      // 옵션
//                "srsName",srsName,              // 옵션
//                "resultType",resultType         // 옵션
        );

        // pnu (필지 고유번호) 로 조회 시 특정 지번에 속한 모든건물 정보를 조회함
        HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);
    }


    /**
     * 용도, 필지고유정보(pnu) 정보를 통해 건물속성 조회
     *
     * @param pnu 필지 고유번호 (시도[2]+시군구[3]+읍면동[3]) = 특정 필지 검색
     * @param mainPrposCode 주요용도코드
     * @param detailPrposCode  세부용도코드
     * @param format 응답결과 형식(xml or json)
     * @param numOfRows 검색건수
     * @param pageNo 페이지 번호
     * @throws Exception
     */
    public void getBuildingUse(String pnu, String mainPrposCode,String detailPrposCode , String format,
                               String numOfRows, String pageNo
                            ) throws  Exception{


        servicePort="";
        String commonPath = "/1611000/nsdi/BuildingUseService";
        String path = "/attr/getBuildingUse";

        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,        // 필수
                "pnu",pnu                       // 필수
//                "mainPrposCode",mainPrposCode,        // 옵션
//                "detailPrposCode",detailPrposCode,    // 옵션
//                "format",format,                      // 옵션
//                "numOfRows",numOfRows,                // 옵션
//                "pageNo",pageNo                       // 옵션
        );

        // pnu (필지 고유번호) 로 조회 시 특정 지번에 속한 모든건물 정보를 조회함
        HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);
    }


}
