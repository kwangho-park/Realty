package kr.com.pkh.batch.basic;

import kr.com.pkh.batch.BatchTestConfig;
import kr.com.pkh.batch.extend.job.standard.StandardConfig;
import kr.com.pkh.batch.openAPI.AptTransactionSvc;
import kr.com.pkh.batch.openAPI.RealEstateTradingSvc;
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



    @Value("${publicDataPotal.openApi.apiKey}")
    private String apiKey;


    // 부동산 거래면적 조회 API
    @Test
    public void realEstateTradingSvcTest() throws IOException, ParseException {

        // given //
        RealEstateTradingSvc realEstateTradingSvc = new RealEstateTradingSvc();

        String method = "GET";
        String path = "getRealEstateTradingArea";

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
        JSONObject jsonObject = realEstateTradingSvc.requestAPI(method, path, parameters);



        // then //

    }


    // error msg : SERVICE ACCESS DENIED ERROR (승인 처리전으로 추정됨)
    // api key 를 인코딩된값, 디코딩된값으로 사용해도 동일한 결과가 반환됨
    // 서울 아파트 매매가 조회
    @Test
    public void AptTransactionSvcTest() throws IOException, ParseException {

        // given //
        AptTransactionSvc aptTransactionSvc = new AptTransactionSvc();

        String method = "GET";
        String path = "";

        Map<String, String> parameters = Map.of(
                "serviceKey",this.apiKey,
                "LAWD_CD", "11110",     // 지역 코드 (법정동코드 10자리 중 앞 5자리)
                "DEAL_YMD", "201512"           // 계약월 (실거래 자료의 계약년월 6자리)
        );


        System.out.println("public data potal API key : "+this.apiKey);

        // when //
        JSONObject jsonObject = aptTransactionSvc.requestAPI(method, path, parameters);



        // then //

    }

    // 서울 아파트 전세가 조회


}
