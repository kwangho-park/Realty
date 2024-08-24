package kr.com.pkh.batch.openAPI.data;

import kr.com.pkh.batch.BatchTestConfig;
import kr.com.pkh.batch.extend.job.standard.StandardJobConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * service name : 국토교통부 건축물대장정보 서비스
 * description : 건축물대장에서 관리하고 있는 총괄표제부, 표제부, 층별개요, 부속지번, 전유공용면적,
 * 오수정화시설, 주택가격, 전유부, 지역지구구역 등 속성정보(대용량 원시DB)
 *
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardJobConfig.class})        // 환경설정 config , test target config
public class BldRgstServiceTest {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    @Test
    public void getBrExposPubuseAreaInfoTest() throws Exception {



        // given
        BldRgstService bldRgstService = new BldRgstService(apiKey);

        String numOfRows="10";
        String pageNo ="1";
        String sigunguCd ="41192";       // 시군구코드(5자리) (필수) : 41192 (경기도 부천시 원미구)
        String bjdongCd ="10800";        // 법정동코드(5자리)  (필수) : 10800 (중동)
        String platGbCd="0";            // 코지구분 (1자리) : 0
        String bun="1051";              // 본번 (4자리) : 1051  (10510000 설악마을아파트 지번)
        String ji="0000";               // 지번 (4자리) : 0000
        String dongNm="310";               // 301 동 = 공급면적 59, 310 동 = 공급면적 69
        String hoNm="";
        String startDate="";
        String endDate ="";

        // when
        bldRgstService.getBrExposPubuseAreaInfo(numOfRows, pageNo, sigunguCd,bjdongCd,platGbCd, bun,ji,
                dongNm, hoNm, startDate, endDate);

        // then

    }

}
