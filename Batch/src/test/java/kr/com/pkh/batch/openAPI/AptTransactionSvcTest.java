package kr.com.pkh.batch.openAPI;

import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.util.json.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Map;

/**
 * 서비스 명 : 아파트 매매 신고 데이터 조회 서비스 (Apartment Transaction Data)
 */
public class AptTransactionSvcTest {


    @Value("${publicDataPotal.openApi.apiKey}")
    private String apiKey;

    // 405 : Method Not Allowed 발생
    // method 변경 및 디코딩 key 로 API 테스트 예정
    @Test
    public void getRTMSDataSvcAptTradeTest() throws  Exception{
        // given //
        AptTransactionSvc aptTransactionSvc = new AptTransactionSvc(apiKey);

        // when //
        JSONObject jsonObject = aptTransactionSvc.getRTMSDataSvcAptTrade("11110", "201512");


        // then //
    }

    // 서울 아파트 전세가 조회
}
