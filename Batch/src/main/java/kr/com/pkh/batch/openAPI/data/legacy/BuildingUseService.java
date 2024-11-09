package kr.com.pkh.batch.openAPI.data.legacy;

import kr.com.pkh.batch.util.HTTPrequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 공급자 : 공공데이터 포털
 * 서비스명 : [서비스 종료 2024.10] 용도별 건물 정보 서비스  (BuildingUseService)
 *  => 대체서비스 : v-world
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
     *
     * 좌표등의 위치정보를 통해 지정한 범위내의 용도별 건물 WFS 조회 (bit map)
     *
     * @param layers 레이어목록
     * @param crs 좌표체계
     * @param bbox 크기범위 (lc1,lc2,uc1,uc2) = 데이터가 조금만 날라져도 이미지가 반환되지않음 (하단좌표, 상단좌표)
     * @param width 너비 (bit map 이미지의 너비)
     * @param height 높이 (bit map 이미지의 높이)
     * @param format 산출물 형식 (png or jpeg or gif)
     * @param transparent 투명여부
     * @param bgcolor 배경색
     * @param exceptions 예외보고
     */
    public void getBuildingUseWMS(String layers, String crs, String bbox, String width,
                                  String height, String format, String transparent, String bgcolor,
                                  String exceptions) throws Exception {
        servicePort="";
        String commonPath = "/1611000/nsdi/BuildingUseService";
        String path = "/wms/getBuildingUseWMS";

        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,        // 필수
                "layers" , layers ,
                "crs" , crs ,               // 필수
                "bbox" , bbox ,                 // 필수
                "width" , width ,               // 필수
                "height" , height ,             // 필수
                "format" , format              // 필수
//                "transparent" , transparent ,
//                "bgcolor" , bgcolor ,
//                "exceptions" , exceptions
        );

        HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);
    }

    /**
     * 좌표등의 위치정보를 통해 지정한 범위내의 용도별 건물 WFS 조회 (vector data for map)
     *
     * @param typeName 피처 유형 (요청 대상인 피처유형이름??)
     * @param bbox 검색범위 (lc1,lc2,uc1,uc2,좌표체계) = 좌표로 검색
     * @param pnu 필지 고유번호 (시도[2]+시군구[3]+읍면동[3]) = 특정 필지 검색
     * @param maxFeatures 피처의 최대값 (max 100)
     * @param srsName 좌표체계 (?)
     * @param resultType 응답형태  ??
     *
     * @return (예정) 건물공간정보 DB 식별자, PNU(고유번호) , 법정동 , 본번/부번 , 용적률 , 용도코드
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
     * 필지고유정보(pnu), 용도 정보를 통해 건물속성 조회
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
