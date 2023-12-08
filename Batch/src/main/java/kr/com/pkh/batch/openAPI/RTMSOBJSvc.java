package kr.com.pkh.batch.openAPI;

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
 * 서비스 명 : 아파트 매매 신고 데이터 조회 서비스(개략/상세)
 * 인증인가 방식 : API key
 * http method : GET 방식만 지원
 *
 * basic uri : http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade
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

//    private String serviceDomain="http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
//    private String serviceDomain="http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
    private String serviceDomain="http://openapi.molit.go.kr";
    private String servicePort=null;
    /**
     * 아파트 매매 신고데이터 개략 조회 <br>
     *
     * @param lawdCd 지역 코드 (법정동코드 10자리 중 앞 5자리)
     * @param dealYmd 계약월 (실거래 자료의 계약년월 6자리)
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public JSONObject getRTMSDataSvcAptTrade(String lawdCd, String dealYmd)throws IOException, ParseException {

        servicePort=":8081";
        String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
        String path = "/getRTMSDataSvcAptTrade";


        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,
                "LAWD_CD",lawdCd,
                "DEAL_YMD",dealYmd
        );



        JSONObject result = HTTPrequest.responseXML(serviceDomain, servicePort, commonPath, path, parameters);

        return result;
    }


    /**
     * 아파트 매매 신고 데이터 상세 조회 <br>
     *
     * @param lawdCd 지역코드 (필수)
     * @param dealYmd 계약월 (필수)
     * @param pageNo 페이지 번호 (옵션)
     * @param numOfRows 한 페이지 row 수 (옵션)
     *
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public JSONObject getRTMSDataSvcAptTradeDev(String lawdCd, String dealYmd, int pageNo, int numOfRows)throws IOException, ParseException {

        servicePort="";
        String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
        String path = "/getRTMSDataSvcAptTradeDev";

        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,
                "LAWD_CD", lawdCd,
                "DEAL_YMD", dealYmd,
                "pageNo",String.valueOf(pageNo),
                "numOfRows",String.valueOf(numOfRows)
        );


        JSONObject result = HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);

        return result;
    }


    /**
     * 아파트 매매 신고 데이터 상세 조회 <br>
     *
     * @param lawdCd 지역코드 (필수)
     * @param dealYmd 계약월 (필수)
     *
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public JSONObject getRTMSDataSvcAptTradeDev(String lawdCd, String dealYmd)throws IOException, ParseException {

        servicePort="/";
        String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
        String path = "/getRTMSDataSvcAptTradeDev";

        Map<String, String> parameters = Map.of(
                "serviceKey",apiKey,
                "LAWD_CD", lawdCd,
                "DEAL_YMD", dealYmd
        );


        JSONObject result = HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);

        return result;
    }

}
