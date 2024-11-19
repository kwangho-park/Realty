package kr.com.pkh.batch.openAPI.data.service;

import kr.com.pkh.batch.dto.api.TradeDTO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.dto.db.PageDTO;
import kr.com.pkh.batch.openAPI.data.parser.BldRgstHubServiceParser;
import kr.com.pkh.batch.util.HTTPrequest;
import kr.com.pkh.batch.util.StringUtil;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 서비스명 :
 * 서비스 설명 :
 * 서비스 인증 방식 : API key
 * 서비스 URI : http://apis.data.go.kr/1613000/BldRgstHubService
 *
 */
@Slf4j
@Component
public class BldRgstHubService {

//    @Value("${publicDataPotal.openApi.apiKey.encoding}")
//    private String apiKey;

    private String serviceDomain="http://apis.data.go.kr/1613000/BldRgstHubService";
    private String servicePort="";  //  없음

    private BldRgstHubServiceParser bldRgstHubServiceParser;

    @Autowired
    public BldRgstHubService(BldRgstHubServiceParser bldRgstHubServiceParser){
        this.bldRgstHubServiceParser=bldRgstHubServiceParser;
    }

    /**
     * 아파트 매매 신고 데이터 상세 조회 (by 지역/기간) <br>
     * description : 건축행정정보시스템(세움터)를 통해 생성된 건축물대장과 관련된 전유/공용면적의 층구분, 층번호, 전유/공용구분, 구조, 용도 등의 정보를 제공
     *
     * [중요] openAPI 테스트 결과 지역코드 (법정동코드 앞 5자리)는 구 (or 군) 단위 구분코드로 조회해야되는것으로 확인됨
     * (가능 : 부천시 원미구 41192, 불가능 : 부천시 41190)
     *
     * @param serviceKey 인증키           (필수값)
     * @param pageNo 페이지 번호
     * @param numOfRows 한 페이지 결과 수 (max 100개)
     * @param sigunguCd 시군구 코드 (length:5) (pnu)  (필수값)
     * @param bjdongCd 법정동 코드 (length:5)  (pnu)  (필수값)
     * @param platGbCd 대지구분 코드 (pnu)
     * @param bun 번  (pnu)
     * @param ji 지  (pnu)
     *
     * @return AptTradeDTO 아파트 매매 신고 상세 데이터
     * @throws IOException
     * @throws ParseException
     */
    public TradeDTO getBrExposPubuseAreaInfo(String serviceKey, String pageNo,
                                             String numOfRows, String sigunguCd,
                                             String bjdongCd, String platGbCd,
                                             String bun, String ji){
        TradeDTO tradeDTO = new TradeDTO();
        String responseXml = null;

        try{
            servicePort="";
            String commonPath = "";
            String path = "/getBrExposPubuseAreaInfo";

            // map 요소 max : 10개
            Map<String, String> parameters = Map.of(
                    "serviceKey",serviceKey,
                    "pageNo", pageNo,
                    "numOfRows", numOfRows,
                    "sigunguCd", sigunguCd,
                    "bjdongCd",bjdongCd,
                    "platGbCd",platGbCd,
                    "bun",bun,
                    "ji",ji

            );


            responseXml = HTTPrequest.responseXML(serviceDomain, servicePort, commonPath, path, parameters);

            log.info("responseXml{}", responseXml);

//            tradeDTO = xmlParsingToObject(responseXml);

        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        return tradeDTO;

    }





}
