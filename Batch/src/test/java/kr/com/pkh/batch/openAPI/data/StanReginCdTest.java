package kr.com.pkh.batch.openAPI.data;


import kr.com.pkh.batch.openAPI.data.service.StanReginCd;
import kr.com.pkh.batch.util.PropertiesUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StanReginCdTest {

    @Test
    public void getStanReginCdList() throws Exception {

        // given
        StanReginCd stanReginCd = new StanReginCd();

        String ServiceKey = PropertiesUtil.getProperty("publicDataPotal.openApi.apiKey.encoding");
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
