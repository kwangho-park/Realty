package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.dto.api.TradePageDTO;
import kr.com.pkh.batch.openAPI.data.parser.RTMSDataSvcParser;
import kr.com.pkh.batch.openAPI.data.service.RTMSDataSvc;
import kr.com.pkh.batch.util.PropertiesUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RTMSDataSvcTest {

    // 아파트 매매 신고 상세 데이터 조회
    @Test
    public void getRTMSDataSvcAptTradeDevTest() throws  Exception{
        String apiKey = PropertiesUtil.getProperty("publicDataPotal.openApi.apiKey.encoding");


        // given //
        RTMSDataSvcParser rtmsDataSvcParser = new RTMSDataSvcParser();
        RTMSDataSvc RTMSDataSvc = new RTMSDataSvc(rtmsDataSvcParser);
        TradePageDTO tradePageDTO = new TradePageDTO();

        String serviceKey=apiKey;
        String pageNo="1";
        String numOfRows="100";
        String LAWD_CD ="41192";        // 11590 : 서울시 동작구, 41192 : 부천시 원미구
        String DEAL_YMD ="202402";


        // when //
        tradePageDTO = RTMSDataSvc.getRTMSDataSvcAptTradeDev(serviceKey, pageNo, numOfRows, LAWD_CD, DEAL_YMD);

        // then //

    }

}
