package kr.com.pkh.batch.openAPI.data.legacy;

import kr.com.pkh.batch.openAPI.data.legacy.RealEstateTradingSvc;
import kr.com.pkh.batch.util.PropertiesUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * 서비스 명 : 부동산 거래현황 통계 조회 서비스 (RealEstateTradingSvc)
 */
@SpringBootTest
class RealEstateTradingSvcTest {



    // 부동산 거래면적 조회 API
    @Test
    public void getRealEstateTradingAreaTest() throws Exception {

        String apiKey = PropertiesUtil.getProperty("publicDataPotal.openApi.apiKey.encoding");


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
