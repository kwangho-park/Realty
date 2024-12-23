package kr.com.pkh.batch.openAPI.data.legacy;

import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.dto.db.PageDTO;
import kr.com.pkh.batch.dto.api.TradePageDTO;
import kr.com.pkh.batch.util.HTTPrequest;
import kr.com.pkh.batch.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 공급자 : 공공데이터 포털
 * 서비스 명 : [서비스 종료 2024.10] 지역과 기간을 설정하여 아파트 매매,전/월세 신고 데이터 조회 서비스(개략/상세) (RTMSOBJSvc)
 *  => 대체 서비스 : 공공데이터포털- 국토교통부_아파트 매매 실거래가 상세 자료
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
     * @param numOfRows 한 페이지의 row 수
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
    public TradePageDTO getRTMSDataSvcAptTradeDev(String serviceKey, String pageNo, String numOfRows,
                                                  String LAWD_CD, String DEAL_YMD) {

        TradePageDTO tradePageDTO = new TradePageDTO();

        try{
            servicePort="/";
            String commonPath = "/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc";
            String path = "/getRTMSDataSvcAptTradeDev";

            Map<String, String> parameters = Map.of(
                    "serviceKey",serviceKey,
                    "pageNo", pageNo,
                    "numOfRows", numOfRows,
                    "LAWD_CD", LAWD_CD,
                    "DEAL_YMD", DEAL_YMD
            );


            String responseXml = HTTPrequest.responseXML(serviceDomain,servicePort, commonPath ,path, parameters);

            tradePageDTO = xmlParsingToObject(responseXml);

        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();

        }finally {
            return tradePageDTO;
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
    // 주소 데이터로 pnu 생성
    public TradePageDTO xmlParsingToObject(String responseXml) throws ParserConfigurationException, IOException, SAXException {


        TradePageDTO tradePageDTO = new TradePageDTO();
        List<AptTradeDTO> aptTradeList = new ArrayList<AptTradeDTO>();
        PageDTO pageDTO = new PageDTO();


        try{

            // http response XML 로그 출력 (for RTMSOBJSvc 서비스) //
            // document 객체로 변환
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(responseXml)));


            // items 데이터 파싱 //
            log.info("[xml element] 아파트 단지별 pnu 출력 START");

            NodeList itemNodes = document.getElementsByTagName("item");

            log.info("total 'item' count : "+itemNodes.getLength());

            for (int i = 0; i < itemNodes.getLength(); i++) {


                AptTradeDTO aptTradeDTO = new AptTradeDTO();

                String childNodesLog="";
                String id="";               // 거래 일련번호
                String pnu = "";
                String name = "";
                String tradeMonth="";
                String tradeYear="";
                String tradeDay="";
                String tradeDate = "";
                int tradeAmount=0;

                Map<String, String> pnuMap = new HashMap<String, String>();

                //            log.debug("element num : "+i);

                NodeList elementList = itemNodes.item(i).getChildNodes();
                //            log.debug("total element count : "+elementList.getLength());

                // 건별 매매 거래 내역
                for(int j=0;j<elementList.getLength();j++){

                    Element element = (Element) elementList.item(j);
                    String tagName = element.getTagName().trim();
                    String tagValue = element.getTextContent().trim();

                    childNodesLog+=(tagName+":"+tagValue+"/");


                    if(tagName.equals("일련번호")) {
                        id = tagValue;
                    }
                    if(tagName.equals("아파트")) {
                        name = tagValue;
                    }
                    if(tagName.equals("거래금액")) {
                        tagValue = StringUtil.removeAllCharsFromString(tagValue, ',');
                        tradeAmount = Integer.parseInt(tagValue);
                    }

                    // 계약년월일
                    if(tagName.equals("년")) {
                        tradeYear = tagValue;
                    }
                    if(tagName.equals("월")) {
                        tradeMonth = StringUtil.padWithZero(tagValue);
                    }
                    if(tagName.equals("일")) {
                        tradeDay = StringUtil.padWithZero(tagValue);
                    }

                    // pnu 일련번호 조합
                    if(tagName.equals("법정동시군구코드")){     // 4-5 자리
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("법정동읍면동코드")){     // 4-5 자리
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("법정동지번코드")){       // 1자리
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("법정동본번코드")){       // 4자리
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("법정동부번코드")){       // 4자리
                        pnuMap.put(tagName,tagValue);
                    }
                }

                //             log.debug("childNodesLog : "+childNodesLog);

                pnu = pnuMap.get("법정동시군구코드") + pnuMap.get("법정동읍면동코드")
                        + pnuMap.get("법정동지번코드")
                        + pnuMap.get("법정동본번코드")  + pnuMap.get("법정동부번코드");

                tradeDate=tradeYear+tradeMonth+tradeDay;

                aptTradeDTO.setId(id);                      // 일련번호 ID
                aptTradeDTO.setPnu(pnu);                    // pnu 필지고유번호
                aptTradeDTO.setTradeAmount(tradeAmount);    // 매매가격
                aptTradeDTO.setTradeDate(tradeDate);        // 거래일자

                aptTradeList.add(aptTradeDTO);

                log.info("apt info : id 일련번호 = "+id +" / pnu = "+pnu+" / name = "+name);
            }

            tradePageDTO.setAptTradeDTOList(aptTradeList);

            // page 데이터 파싱 //

            String pageNo="";
            String numOfRows="";
            String totalCount="";

            // NodeList 반환
            NodeList nodeList = document.getElementsByTagName("body");
            for (int i = 0; i < nodeList.getLength(); i++) {

                NodeList elementList = nodeList.item(i).getChildNodes();

                for(int j=0;j<elementList.getLength();j++){

                    Element element = (Element) elementList.item(j);
                    String tagName = element.getTagName().trim();
                    String tagValue = element.getTextContent().trim();

                    if(tagName.equals("pageNo")) {
                        pageNo = tagValue;
                    }
                    if(tagName.equals("numOfRows")) {
                        numOfRows = tagValue;
                    }
                    if(tagName.equals("totalCount")) {
                        totalCount = tagValue;
                    }
                }
            }

            pageDTO.setPageNo(pageNo);
            pageDTO.setNumOfRows(numOfRows);
            pageDTO.setTotalCount(totalCount);

            tradePageDTO.setPageDTO(pageDTO);


        }catch(NumberFormatException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();

        }finally {
            log.info("[xml element] 아파트 단지별 pnu 출력 END");
            return tradePageDTO;

        }


    }

}
