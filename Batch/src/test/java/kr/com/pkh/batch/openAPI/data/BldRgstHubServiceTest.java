package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.dto.api.PubuseAreaPageDTO;
import kr.com.pkh.batch.openAPI.data.parser.BldRgstHubServiceParser;
import kr.com.pkh.batch.openAPI.data.service.BldRgstHubService;
import kr.com.pkh.batch.util.PropertiesUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BldRgstHubServiceTest {

    @Test
    public void getBrExposPubuseAreaInfoTest() throws  Exception{


        // given //
        String apiKey = PropertiesUtil.getProperty("publicDataPotal.openApi.apiKey.encoding");
        BldRgstHubServiceParser bldRgstHubServiceParser = new BldRgstHubServiceParser();

        BldRgstHubService bldRgstHubService = new BldRgstHubService(bldRgstHubServiceParser);
        PubuseAreaPageDTO pubuseAreaPageDTO = new PubuseAreaPageDTO();

        String serviceKey=apiKey;
        String pageNo="1";
        String numOfRows="100";

        String sigunguCd ="41192";      // 시군구코드(5자리) (필수) : 41192 (경기도 부천시 원미구)
        String bjdongCd ="10800";       // 법정동코드(5자리)  (필수) : 10800 (중동)
        String platGbCd="0";            // 토지구분 (1자리) : 0
        String bun="1029";              // 본번 (4자리) : 1051  (10510000 설악마을아파트 지번)
        String ji="0000";               // 지번 (4자리) : 0000

        String pnu="";

        // when //
        pubuseAreaPageDTO = bldRgstHubService.getBrExposPubuseAreaInfo(
                serviceKey,  pageNo,
                numOfRows,  sigunguCd,
                bjdongCd, platGbCd,
                bun, ji, pnu);

        // then //



    }
}
