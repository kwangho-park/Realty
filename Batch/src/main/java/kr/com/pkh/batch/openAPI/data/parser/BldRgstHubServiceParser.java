package kr.com.pkh.batch.openAPI.data.parser;


import kr.com.pkh.batch.dto.api.PubuseAreaDTO;
import kr.com.pkh.batch.dto.api.PubuseAreaPageDTO;
import kr.com.pkh.batch.dto.db.PageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Slf4j
@Component
public class BldRgstHubServiceParser {


    /**
     * 아파트별 면적타입 데이터 파싱
     *
     * @param responseXml
     * @return
     * @throws Exception
     */
    public PubuseAreaPageDTO xmlParsingToObject(String responseXml) throws Exception{

        PubuseAreaPageDTO pubuseAreaPageDTO = new PubuseAreaPageDTO();      // xml string 의 page 전체 데이터를 저장

        PageDTO pageDTO = new PageDTO();


        try{

            // http response XML 로그 출력 //
            // document 객체로 변환
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(responseXml)));

            log.info("[xml parsing] 아파트별 면적타입 데이터 파싱 START");

            // item 데이터 파싱 //
            NodeList itemNodes = document.getElementsByTagName("item");

            log.info("total 'item' count : "+itemNodes.getLength());

            // <items>
            for (int i = 0; i < itemNodes.getLength(); i++) {
                PubuseAreaDTO pubuseAreaDTO = new PubuseAreaDTO();

                // 수집용 데이터
                String childNodesLog=null;
                String area=null;          // 면적 (전용 or 공용)
                String publicArea=null;      // 공용면적
                String privateArea=null;     // 전용면적
                String mgmBldrgstPk=null;    // 관리 건축물 대장 (세대별 고유값)
                String pnu=null;
                String bldNm=null;           // 아파트명
                String platPlc=null;         // 구주소
                String newPlatPlc=null;      // 도로명 주소

                // 로그용 데이터
                String dongNm=null;                 // 동
                String hoNm=null;                    // 호수
                String exposPubuseGbCd=null;      // 면적코드
                String exposPubuseGbCdNm=null;           // 면적명
                String mainPurpsCdNm=null;               // 주용도 코드명


                NodeList elementList = itemNodes.item(i).getChildNodes();

                // <item>
                // 아파트별, 면적타입별 데이터 리스트
                for(int j=0;j<elementList.getLength();j++){
                    Element element = (Element) elementList.item(j);
                    String tagName = element.getTagName().trim();
                    String tagValue = element.getTextContent().trim();

                    childNodesLog+=(tagName+":"+tagValue+"/");


                    if(tagName.equals("mgmBldrgstPk")){     // 관리 건축물 대장 (세대별 고유값)
                        mgmBldrgstPk=tagValue;
                    }
                    if(tagName.equals("bldNm")){        // 아파트명
                        bldNm=tagValue;
                    }
                    if(tagName.equals("dongNm")){       // 동
                        dongNm=tagValue;
                    }
                    if(tagName.equals("hoNm")){         // 호수
                        hoNm=tagValue;
                    }
                    if(tagName.equals("platPlc")){         // 구주소
                        platPlc=tagValue;
                    }
                    if(tagName.equals("newPlatPlc")){         // 도로명 주소
                        newPlatPlc=tagValue;
                    }



                    if(tagName.equals("mainPurpsCdNm")){           // 면적 용도
                        mainPurpsCdNm=tagValue;
                    }
                    if(tagName.equals("exposPubuseGbCd")){        // 전유(전용), 공용 구분코드
                        exposPubuseGbCd=tagValue;
                    }
                    if(tagName.equals("exposPubuseGbCdNm")){       // 전유(전용), 공용 구분코드명
                        exposPubuseGbCdNm=tagValue;
                    }
                    if(tagName.equals("area")){         // 면적 (전용 or 공용)
                        area=tagValue;
                    }

                    if(exposPubuseGbCd.equals("1")) {        // 전유면적 (전용면적)
                        privateArea=tagValue;
                    }else{                                  // 공용면적
                        publicArea=tagValue;
                    }

                }

                pubuseAreaDTO.setMgmBldrgstPk(mgmBldrgstPk);
                pubuseAreaDTO.setBldNm(bldNm);
                pubuseAreaDTO.setPlatPlc(platPlc);
                pubuseAreaDTO.setNewPlatPlc(newPlatPlc);


                // 면적타입 설정 //
                // 건축물대장정보가 존재하는 경우 <item> 추가를 스킵하고, 기존 PubuseAreaPageDTO 의 면적정보만 업데이트함
                if(pubuseAreaPageDTO.isMgmBidrgstPk(mgmBldrgstPk)){
                    if(pubuseAreaPageDTO.getAreaType(mgmBldrgstPk).equals("privateArea")) {        // 전유면적 (전용면적)
                        pubuseAreaPageDTO.updatePrivateArea(mgmBldrgstPk,area);
                        continue;
                    }else{                                  // 공용면적
                        pubuseAreaPageDTO.updatePublicArea(mgmBldrgstPk,area);
                        continue;
                    }

                // <item> 을 PubuseAreaPageDTO 에 추가함
                }else{
                    if(exposPubuseGbCd.equals("1")) {        // 전유면적 (전용면적)
                        pubuseAreaDTO.setPrivateArea(area);
                        pubuseAreaPageDTO.getPubuseAreaDTOList().add(pubuseAreaDTO);
                    }else{                                  // 공용면적
                        pubuseAreaDTO.setPublicArea(area);
                        pubuseAreaPageDTO.getPubuseAreaDTOList().add(pubuseAreaDTO);
                    }
                };
            }   // for() END


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

            pubuseAreaPageDTO.setPageDTO(pageDTO);

        }catch(NumberFormatException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            log.info("[xml element] 아파트별 면적타입 데이터 파싱 END");
        }
        return pubuseAreaPageDTO;
    }



}
