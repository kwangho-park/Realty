package kr.com.pkh.batch.openAPI.data.parser;

import kr.com.pkh.batch.dto.api.TradePageDTO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.dto.db.PageDTO;
import kr.com.pkh.batch.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Component
public class RTMSDataSvcParser{


    // 아파트 매매 상세 데이터 파싱
    // 주소 데이터로 pnu 생성
    public TradePageDTO xmlParsingToObject(String responseXml) throws ParserConfigurationException, IOException, SAXException {


        TradePageDTO tradePageDTO = new TradePageDTO();
        List<AptTradeDTO> aptTradeList = new ArrayList<AptTradeDTO>();
        PageDTO pageDTO = new PageDTO();


        try{

            // http response XML 로그 출력 //
            // document 객체로 변환
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(responseXml)));


            // items 데이터 파싱 //
            log.info("[xml parsing] 아파트 매매 상세 데이터 파싱 START");

            NodeList itemNodes = document.getElementsByTagName("item");

            log.info("total 'item' count : "+itemNodes.getLength());


            // <items>
            for (int i = 0; i < itemNodes.getLength(); i++) {


                AptTradeDTO aptTradeDTO = new AptTradeDTO();

                String childNodesLog="";
                String id="";               // 거래 일련번호 ([중요] 서비스가 변경되면서 단지 일련번호로 변경된건지 검증이 필요)
                String pnu = "";
//                String name = "";
                String tradeMonth="";
                String tradeYear="";
                String tradeDay="";
                String tradeDate = "";
                int tradeAmount=0;

                Map<String, String> pnuMap = new HashMap<String, String>();

                //            log.debug("element num : "+i);

                NodeList elementList = itemNodes.item(i).getChildNodes();
                //            log.debug("total element count : "+elementList.getLength());

                // <item>
                // 건별 매매 거래 내역
                for(int j=0;j<elementList.getLength();j++){

                    Element element = (Element) elementList.item(j);
                    String tagName = element.getTagName().trim();
                    String tagValue = element.getTextContent().trim();

                    childNodesLog+=(tagName+":"+tagValue+"/");


                    if(tagName.equals("aptSeq")) {
                        id = tagValue;
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
                    if(tagName.equals("sggCd")){              // 시군구코드 (4-5 자리)
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("umdCd")){        // 읍면동코드 (4-5 자리)
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("landCd")){       // 대지구분 코드 (1자리)
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("bonbun")){       // 본번 코드 (4자리)
                        pnuMap.put(tagName,tagValue);
                    }else if(tagName.equals("bubun")){        // 지번 코드 (4자리)
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
                aptTradeDTO.setTradeAmount(tradeAmount);    // 매매가격
                aptTradeDTO.setTradeDate(tradeDate);        // 거래일자

                aptTradeDTO.setSigunCd(pnuMap.get("sggCd"));
                aptTradeDTO.setBjdCd(pnuMap.get("umdCd"));
                aptTradeDTO.setPlatCd(pnuMap.get("landCd"));
                aptTradeDTO.setBunCd(pnuMap.get("bonbun"));
                aptTradeDTO.setJiCd(pnuMap.get("bubun"));

                aptTradeList.add(aptTradeDTO);

                log.info("apt info : id 일련번호 = {} / pnu = {} ",id ,pnu);
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
            log.info("[xml parsing] 아파트 매매 상세 데이터 파싱 END");
            return tradePageDTO;

        }


    }


}
