package kr.com.pkh.batch.openAPI.data.service;

import kr.com.pkh.batch.dto.api.TradePageDTO;
import kr.com.pkh.batch.openAPI.data.parser.RTMSDataSvcParser;
import kr.com.pkh.batch.util.HTTPrequest;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    private String serviceDomain="http://apis.data.go.kr";
    private String servicePort="";  //  없음

    private RTMSDataSvcParser rtmsDataSvcParser;



    @Autowired
    public RTMSDataSvc(RTMSDataSvcParser rtmsDataSvcParser){
        this.rtmsDataSvcParser=rtmsDataSvcParser;
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
                                                  String LAWD_CD, String DEAL_YMD){
        TradePageDTO tradePageDTO = new TradePageDTO();
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

            tradePageDTO = this.rtmsDataSvcParser.xmlParsingToObject(responseXml);

        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        return tradePageDTO;

    }


}
