package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.dto.api.TradeDTO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.dto.db.PageDTO;
import kr.com.pkh.batch.util.HTTPrequest;
import kr.com.pkh.batch.util.StringUtil;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * provider : 공공데이터 포털
 * 서비스명 : 아파트 매매 실거래가 상세 자료
 * 서비스 설명 : 지역코드와 기간을 설정하여 해당지역, 해당기간의 아파트 매매 상세 자료를 제공하는 아파트 매매 실거래가 상세 자료 조회
 * 서비스 인증 방식 : API key
 * 서비스  url  : http://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev
 *
 *
 */
@Slf4j
@Component
public class RTMSDataSvc {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    public RTMSDataSvc(){}

    private String serviceDomain="http://apis.data.go.kr";
    private String servicePort="";  //  없음

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
    public TradeDTO getRTMSDataSvcAptTradeDev(String serviceKey, String pageNo, String numOfRows,
                                          String LAWD_CD, String DEAL_YMD){
        TradeDTO tradeDTO = new TradeDTO();
        String responseXml = null;

        try{
            servicePort="";
            String commonPath = "/1613000/RTMSDataSvcAptTradeDev";
            String path = "/getRTMSDataSvcAptTradeDev";


            Map<String, String> parameters = Map.of(
                    "serviceKey",serviceKey,
                    "pageNo", pageNo,
                    "numOfRows", numOfRows,
                    "LAWD_CD", LAWD_CD,
                    "DEAL_YMD", DEAL_YMD
            );

            responseXml = HTTPrequest.responseXML(serviceDomain, servicePort, commonPath, path, parameters);

            tradeDTO = xmlParsingToObject(responseXml);

        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        return tradeDTO;

    }



    // 아파트 매매 상세 데이터 파싱 (xml string -> java Map)
    // 주소 데이터로 pnu 생성
    public TradeDTO xmlParsingToObject(String responseXml) throws ParserConfigurationException, IOException, SAXException {


        TradeDTO tradeDTO = new TradeDTO();
        List<AptTradeDTO> aptTradeList = new ArrayList<AptTradeDTO>();
        PageDTO pageDTO = new PageDTO();


        try{

            // http response XML 로그 출력 (for RTMSDataSvc 서비스) //
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
                String id="";               // 거래 일련번호 ([중요] 서비스가 변경되면서 단지 일련번호로 변경된건지 검증이 필요)
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


                    if(tagName.equals("aptSeq")) {
                        id = tagValue;
                    }
                    if(tagName.equals("aptNm")) {
                        name = tagValue;
                    }
                    if(tagName.equals("dealAmount")) {
                        tagValue = StringUtil.removeAllCharsFromString(tagValue, ',');
                        tradeAmount = Integer.parseInt(tagValue);
                    }

                    // 계약년월일
                    if(tagName.equals("dealYear")) {
                        tradeYear = tagValue;
                    }
                    if(tagName.equals("dealMonth")) {
                        tradeMonth = StringUtil.padWithZero(tagValue);
                    }
                    if(tagName.equals("dealDay")) {
                        tradeDay = StringUtil.padWithZero(tagValue);
                    }

                    // pnu 일련번호 조합
                    if(tagName.equals("sggCd")){     // 4-5 자리
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("umdCd")){     // 4-5 자리
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("landCd")){       // 1자리
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("bonbun")){       // 4자리
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("bubun")){       // 4자리
                        pnuMap.put(tagName,tagValue);
                    }
                }

                //             log.debug("childNodesLog : "+childNodesLog);

                pnu = pnuMap.get("sggCd") + pnuMap.get("umdCd")
                        + pnuMap.get("landCd")
                        + pnuMap.get("bonbun")  + pnuMap.get("bubun");

                tradeDate=tradeYear+tradeMonth+tradeDay;

                aptTradeDTO.setId(id);                      // 일련번호 ID
                aptTradeDTO.setPnu(pnu);                    // pnu 필지고유번호
                aptTradeDTO.setName(name);                  // 아파트명
                aptTradeDTO.setTradeAmount(tradeAmount);    // 매매가격
                aptTradeDTO.setTradeDate(tradeDate);        // 거래일자

                aptTradeList.add(aptTradeDTO);

                log.info("apt info : id 일련번호 = "+id +" / pnu = "+pnu+" / name = "+name);
            }

            tradeDTO.setAptTradeDTOList(aptTradeList);

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

            tradeDTO.setPageDTO(pageDTO);


        }catch(NumberFormatException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();

        }finally {
            log.info("[xml element] 아파트 단지별 pnu 출력 END");
            return tradeDTO;

        }


    }



}
