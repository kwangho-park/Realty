package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.BatchTestConfig;
import kr.com.pkh.batch.extend.job.standard.StandardConfig;
import kr.com.pkh.batch.openAPI.data.RTMSOBJSvc;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 서비스 명 : 아파트 매매,전/월세 신고 데이터 조회 서비스 (Apartment Transaction Data)
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardConfig.class})        // 환경설정 config , test target config
public class RTMSOBJSvcTest {


    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    // [미사용] 아파트 매매 신고 개략 데이터 조회
    @Test
    public void getRTMSDataSvcAptTradeTest() throws  Exception{
        // given //
        RTMSOBJSvc RTMSOBJSvc = new RTMSOBJSvc(apiKey);

        // when //
        // 지역코드 11590 : 서울시 동작구
        // 지역코드 41192 : 부천시 원미구
        RTMSOBJSvc.getRTMSDataSvcAptTrade("11140", "202402");

        // then //
    }


    // 아파트 매매 신고 상세 데이터 조회
    @Test
    public void getRTMSDataSvcAptTradeDevTest() throws  Exception{

        // given //
        RTMSOBJSvc RTMSOBJSvc = new RTMSOBJSvc();

        String serviceKey=this.apiKey;
        String pageNo="1";
        String numOfRows="100";
        String LAWD_CD ="41192";        // 11590 : 서울시 동작구, 41192 : 부천시 원미구
        String DEAL_YMD ="202402";


        // when //
        RTMSOBJSvc.getRTMSDataSvcAptTradeDev(serviceKey, pageNo, numOfRows, LAWD_CD, DEAL_YMD);

        // then //
    }

    // 아파트 전/월세 신고 데이터 조회
    @Test
    public void getRTMSDataSvcAptRentTest() throws  Exception{
        // given //
        RTMSOBJSvc RTMSOBJSvc = new RTMSOBJSvc(apiKey);

        // when //
        RTMSOBJSvc.getRTMSDataSvcAptRent("41190", "202312");

        // then //
    }

}
