package kr.com.pkh.batch.openAPI;

import kr.com.pkh.batch.BatchTestConfig;
import kr.com.pkh.batch.extend.job.standard.StandardConfig;
import kr.com.pkh.batch.util.json.JSONObject;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 서비스 명 : 아파트 매매 신고 데이터 조회 서비스 (Apartment Transaction Data)
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardConfig.class})        // 환경설정 config , test target config
public class RTMSOBJSvcTest {


    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    @Test
    public void getRTMSDataSvcAptTradeTest() throws  Exception{
        // given //
        RTMSOBJSvc RTMSOBJSvc = new RTMSOBJSvc(apiKey);

        // when //
        JSONObject jsonObject = RTMSOBJSvc.getRTMSDataSvcAptTrade("11110", "201512");


        // then //
    }


    @Test
    public void getRTMSDataSvcAptTradeDevTest() throws  Exception{
        // given //
        RTMSOBJSvc RTMSOBJSvc = new RTMSOBJSvc(apiKey);

        // when //
        JSONObject jsonObject = RTMSOBJSvc.getRTMSDataSvcAptTradeDev("11110", "201512");

        // then //
    }
}
