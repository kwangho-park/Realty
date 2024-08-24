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

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardJobConfig.class})        // 환경설정 config , test target config
public class StanReginCdTest {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    @Test
    public void getStanReginCdList() throws Exception {

        // given
        StanReginCd stanReginCd = new StanReginCd();

        String ServiceKey = apiKey;
        String type = "xml";
        String pageNo = "1";
        String numOfRows="1";
        String flag="1";
        String locatadd_nm="경기도";       // HTTP ROUTING ERROR 발생  (web browser 요청 시 정상 )

        // when
        stanReginCd.getStanReginCdList(ServiceKey, type, pageNo, numOfRows, flag, locatadd_nm);

        // then
    }

}
