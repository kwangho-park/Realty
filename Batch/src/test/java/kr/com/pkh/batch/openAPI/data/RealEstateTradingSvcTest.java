package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.BatchTestConfig;
import kr.com.pkh.batch.extend.job.standard.StandardConfig;
import kr.com.pkh.batch.openAPI.data.RealEstateTradingSvc;
import kr.com.pkh.batch.util.json.parser.ParseException;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


/**
 * 서비스 명 : 부동산 거래현황 통계 조회 서비스 (RealEstateTradingSvc)
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardConfig.class})        // 환경설정 config , test target config
class RealEstateTradingSvcTest {


    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;


    // 부동산 거래면적 조회 API
    @Test
    public void getRealEstateTradingAreaTest() throws IOException, ParseException {

        // given //
        RealEstateTradingSvc realEstateTradingSvc = new RealEstateTradingSvc(apiKey);


        // when //
        realEstateTradingSvc.getRealEstateTradingArea(
                1,10,
                "202301","202304",
                "11000","05");

        // then //

    }


}
