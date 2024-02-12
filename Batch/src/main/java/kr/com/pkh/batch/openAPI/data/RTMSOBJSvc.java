package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.dto.AptTradeDTO;
import kr.com.pkh.batch.util.HTTPrequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    public void getRTMSDataSvcAptTrade(String lawdCd, String dealYmd) {

        try{
            servicePort=":8081";
            String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
            String path = "/getRTMSDataSvcAptTrade";


            Map<String, String> parameters = Map.of(
                    "serviceKey",apiKey,
                    "LAWD_CD",lawdCd,
                    "DEAL_YMD",dealYmd
            );

            String responseXml = HTTPrequest.responseXML(serviceDomain, servicePort, commonPath, path, parameters);


        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
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
    public void getRTMSDataSvcAptTradeDev(String lawdCd, String dealYmd, int pageNo, int numOfRows)  {

        try{
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


            String responseXml = HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);

        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 아파트 매매 신고 데이터 상세 조회 (by 지역/기간) <br>
     *
     * [중요] openAPI 테스트 결과 지역코드 (법정동코드 앞 5자리)는 구 (or 군) 단위 구분코드로 조회해야되는것으로 확인됨
     * (가능 : 부천시 원미구 41192, 불가능 : 부천시 41190)
     *
     * @param serviceKey 인증키
     * @param pageNo 페이지 번호
     * @param numOfRows 한 페이지 결과 수
     * @param LAWD_CD 지역코드 5자리 (필수)
     * @param DEAL_YMD  계약월 6자리 (필수)
     *
     * @return AptTradeDTO 아파트 매매 신고 상세 데이터
     * @throws IOException
     * @throws ParseException
     */
    public  List<String> getRTMSDataSvcAptTradeDev(String serviceKey, String pageNo, String numOfRows,
                                          String LAWD_CD, String DEAL_YMD) {

        List<String> aptTradeList = new ArrayList<String>();

        try{
            servicePort="/";
            String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
            String path = "/getRTMSDataSvcAptTradeDev";

            Map<String, String> parameters = Map.of(
                    "serviceKey",serviceKey,
                    // "pageNo", pageNo,
                    // "numOfRows", numOfRows,
                    "LAWD_CD", LAWD_CD,
                    "DEAL_YMD", DEAL_YMD
            );


            String responseXml = HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);

            aptTradeList = xmlParsingToObject(responseXml);

        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();

        }finally {
            return aptTradeList;
        }
    }


    /**
     * 전월세 신고 데이터 조회 (by 지역/기간)
     * @param lawdCd 지역코드 5자리 (필수)
     * @param dealYmd 계약월 6자리 (필수)
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public void getRTMSDataSvcAptRent(String lawdCd, String dealYmd) {

        try{
            servicePort=":8081";
            String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
            String path = "/getRTMSDataSvcAptRent";

            Map<String, String> parameters = Map.of(
                    "serviceKey",apiKey,
                    "LAWD_CD", lawdCd,
                    "DEAL_YMD", dealYmd
            );

            String responseXml = HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);

        }catch(ParseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    // 아파트 매매 상세 데이터 파싱 (xml string -> java Map)
    public List<String> xmlParsingToObject(String responseXml) throws ParserConfigurationException, IOException, SAXException {

        // test  1
//        AptTradeDTO aptTradeDTO = new AptTradeDTO();
//        Map<String, String> pnuMap = new HashMap<>();      // key : 단지명 , value : pnu


        // test 2
        List<String> aptTradeList = new ArrayList<String>();


        // http response XML 로그 출력 (for RTMSOBJSvc 서비스) //
        // document 객체로 변환
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(responseXml)));


        log.info("[xml element] 아파트 단지별 pnu 출력 START");

        NodeList itemNodes = document.getElementsByTagName("item");

        log.info("total 'item' count : "+itemNodes.getLength());

        for (int i = 0; i < itemNodes.getLength(); i++) {

            // test 2
            AptTradeDTO aptTradeDTO = new AptTradeDTO();

            String childNodesLog="";
            String pnu = "";
            Map<String, String> pnuData = new HashMap<String, String>();

//            log.debug("element num : "+i);

            NodeList elementList = itemNodes.item(i).getChildNodes();
//            log.debug("total element count : "+elementList.getLength());

            // 건별 거래 내역
            for(int j=0;j<elementList.getLength();j++){

                Element element = (Element) elementList.item(j);
                String tagName = element.getTagName().trim();
                String tagValue = element.getTextContent().trim();
                childNodesLog+=(tagName+":"+tagValue+"/");

                // pnu 조합
                if(tagName.equals("아파트")){
                    pnuData.put(tagName,tagValue);
                }else if(tagName.equals("법정동시군구코드")){     // 4-5 자리
                    pnuData.put(tagName,tagValue);
                }else if(tagName.equals("법정동읍면동코드")){     // 4-5 자리
                    pnuData.put(tagName,tagValue);
                }else if(tagName.equals("법정동지번코드")){       // 1자리
                    pnuData.put(tagName,tagValue);
                }else if(tagName.equals("법정동본번코드")){       // 4자리
                    pnuData.put(tagName,tagValue);
                }else if(tagName.equals("법정동부번코드")){       // 4자리
                    pnuData.put(tagName,tagValue);
                }
            }

//             log.debug("childNodesLog : "+childNodesLog);

            pnu = pnuData.get("법정동시군구코드") + pnuData.get("법정동읍면동코드")
                    + pnuData.get("법정동지번코드")
                    + pnuData.get("법정동본번코드")  + pnuData.get("법정동부번코드");

            // test  1
//            pnuMap.put(pnuData.get("아파트"),pnu);           //key : 아파트명, value : pnu

            // test 2
            aptTradeList.add(pnuData.get("아파트"));       // 아파트명
        }

        // test 1
//        for (Map.Entry<String, String> entry : pnuMap.entrySet()) {
//            log.info(entry.getKey() + " : " + entry.getValue());
//        }

        // test 1
//        aptTradeDTO.setPnuMap(pnuMap);


        log.info("[xml element] 아파트 단지별 pnu 출력 END");

        return aptTradeList;

    }


}
