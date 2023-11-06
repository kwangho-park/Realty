package kr.com.pkh.batch.basic;

import kr.com.pkh.batch.BatchTestConfig;
import kr.com.pkh.batch.extend.job.standard.StandardConfig;
import kr.com.pkh.batch.publicData.PublicData;
import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.util.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardConfig.class})        // 환경설정 config , test target config
class OpenAPITest {



    @Value("${publicData.openApi.apiKey}")
    private String apiKey;


    @Test
    public void requestWebAPITest() throws IOException, ParseException {

        // given //
        PublicData publicData = new PublicData();

        String method = "GET";
        String path = "getRealEstateTradingArea";       // 부동산 거래면적 조회 API

        Map<String, String> parameters = Map.of(
                "page", "1",            // default
                "perPage", "10",            // default
                "serviceKey",this.apiKey,
                URLEncoder.encode("cond[RESEARCH_DATE::GTE]","UTF-8"),"202301",  // 조사 시작일자
                URLEncoder.encode("cond[RESEARCH_DATE::LTE]","UTF-8"),"202304",  // 조사 종료일자
                URLEncoder.encode("cond[REGION_CD::EQ]","UTF-8"),"11000",        // 지역코드 (11000 : 서울)
                URLEncoder.encode("cond[DEAL_OBJ::EQ]","UTF-8"),"05"                 // 거래유형 (05: 아파트)
        );


        System.out.println("public data potal API key : "+this.apiKey);

        // when //
        JSONObject jsonObject = publicData.requestOpenAPI(method, path, parameters);



        // then //


    }


}
