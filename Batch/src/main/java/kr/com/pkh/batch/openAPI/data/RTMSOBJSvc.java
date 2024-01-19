package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.util.HTTPrequest;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 공급자 : 공공데이터 포털
 * 서비스 명 : 지역과 기간을 설정하여 아파트 매매,전/월세 신고 데이터 조회 서비스(개략/상세) (RTMSOBJSvc)
 *
 * - 조회 데이터가 없는 경우 반환 데이터 : http 200 code 반환, http body (xml) 의  <resultCode>00</<resultCode> , <resultMsg> NORMAL SERVICE.</resultMsg>
 *
 * -인증인가 방식 : API key <br>
 * - http method : GET 방식만 지원 <br>
 *
 * domain : http://openapi.molit.go.kr <br>
 *
 *
 */
@Slf4j
@Component
public class RTMSOBJSvc {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    public RTMSOBJSvc(){}

    public RTMSOBJSvc(String apiKey){
        this.apiKey = apiKey;
    }

    private String serviceDomain="http://openapi.molit.go.kr";
    private String servicePort=null;
    /**
     * 아파트 매매 신고데이터 개략 조회 (by 지역/기간) <br>
     *
     * @param lawdCd 지역 코드 (법정동코드 10자리 중 앞 5자리)
     * @param dealYmd 계약월 (실거래 자료의 계약년월 6자리)
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public void getRTMSDataSvcAptTrade(String lawdCd, String dealYmd)throws IOException, ParseException {

        servicePort=":8081";
        String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
        String path = "/getRTMSDataSvcAptTrade";


        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,
                "LAWD_CD",lawdCd,
                "DEAL_YMD",dealYmd
        );



        HTTPrequest.responseXML(serviceDomain, servicePort, commonPath, path, parameters);

    }


    /**
     * 아파트 매매 신고 데이터 상세 조회  (by 지역/기간)  <br>
     *
     * @param lawdCd 지역코드
     * @param dealYmd 계약월
     * @param pageNo 페이지 번호
     * @param numOfRows 한 페이지 row 수
     *
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public void getRTMSDataSvcAptTradeDev(String lawdCd, String dealYmd, int pageNo, int numOfRows)throws IOException, ParseException {

        servicePort="";
        String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
        String path = "/getRTMSDataSvcAptTradeDev";

        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,        // 필수
                "LAWD_CD", lawdCd,              // 필수
                "DEAL_YMD", dealYmd,            // 필수
                "pageNo",String.valueOf(pageNo),        // 옵션
                "numOfRows",String.valueOf(numOfRows)   // 옵션
        );


        HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);

    }


    /**
     * 아파트 매매 신고 데이터 상세 조회 (by 지역/기간) <br>
     *
     * @param lawdCd 지역코드 5자리 (필수)
     * @param dealYmd 계약월 6자리 (필수)
     *
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public void getRTMSDataSvcAptTradeDev(String lawdCd, String dealYmd)throws IOException, ParseException {

        servicePort="/";
        String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
        String path = "/getRTMSDataSvcAptTradeDev";

        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,
                "LAWD_CD", lawdCd,
                "DEAL_YMD", dealYmd
        );


        HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);

    }


    /**
     * 전월세 신고 데이터 조회 (by 지역/기간)
     * @param lawdCd 지역코드 5자리 (필수)
     * @param dealYmd 계약월 6자리 (필수)
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public void getRTMSDataSvcAptRent(String lawdCd, String dealYmd) throws IOException, ParseException{
        servicePort=":8081";
        String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
        String path = "/getRTMSDataSvcAptRent";

        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,
                "LAWD_CD", lawdCd,
                "DEAL_YMD", dealYmd
        );

        HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);

    }


}
