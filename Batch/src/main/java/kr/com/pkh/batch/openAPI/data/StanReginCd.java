package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.util.HTTPrequest;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 행정표준코드관리시스템에서 제공중인 법정동코드 정보
 */
@Slf4j
@Component
public class StanReginCd {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    public StanReginCd(){}

    public StanReginCd(String apiKey) {this.apiKey = apiKey;}

    private String serviceDomain="http://apis.data.go.kr";

    private String servicePort="";

    /**
     * 법정동코드 조회
     * [이슈] HTTP ROUTING ERROR 에러 발생 (browser 에서 해당 URI 를 요청하는 경우는 정상 )
     *
     *
     * @param ServiceKey
     * @param type 호출문서(xml, json)
     * @param pageNo 페이지 위치
     * @param numOfRows 페이지 당 요청 숫자
     * @param flag 신규API
     * @param locatadd_nm 지역주소명
     *
     * @throws IOException
     * @throws ParseException
     */
    public void getStanReginCdList(String ServiceKey, String type, String pageNo, String numOfRows, String flag, String locatadd_nm) throws IOException, ParseException {

        String commonPath = "/1741000/StanReginCd";
        String path = "/getStanReginCdList";


        Map<String, String> parameters = Map.of(
                "ServiceKey",ServiceKey,
                "type",type,
                "pageNo",pageNo,
                "numOfRows",numOfRows,
                "flag",flag,
                "locatadd_nm",locatadd_nm

        );

        HTTPrequest.responseXML(serviceDomain, servicePort, commonPath, path, parameters);

    }


}
