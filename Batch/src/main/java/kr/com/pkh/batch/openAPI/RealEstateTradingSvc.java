package kr.com.pkh.batch.openAPI;

import kr.com.pkh.batch.util.HTTPrequest;
import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.util.json.parser.JSONParser;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 서비스 명 : 부동산 거래현황 통계 조회 서비스 (RealEstateTradingSvc)
 * 인증인가 방식 : API key
 * http method : GET 방식만 지원
 *
 * @Description 기본 parameter (page, perPage) 는 소문자이며, 이외 parameter 는 모두 대문자로 표기 <br>
 * @Description 기본 이외 parameter 는 특정 형식으로 URL encoding (UTF-8) 필요 <br>
 *
 * basic uri : https://api.odcloud.kr/api/RealEstateTradingSvc/v1/상세서비스명?page=1&perPage=10&serviceKey=서비스키
 *
 */
@Slf4j
@Component
public class RealEstateTradingSvc {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    // constructor //
    public RealEstateTradingSvc(){}
    public RealEstateTradingSvc(String apiKey){
        this.apiKey = apiKey;
    }

    private String serviceDomain="https://api.odcloud.kr/api/RealEstateTradingSvc/v1";

    /**
     *
     * @param page             : 명세서에 정의 미표기
     * @param perPage          : 명세서에 정의 미표기
     * @param researchDate_gte : 조사 시작일자 (옵션)
     * @param researchDate_lte : 조사 종료일자 (옵션)
     * @param regionCd_eq : 지역코드 (11000 : 서울) (옵션)
     * @param dealObj_eq : 거래유형 (05: 아파트) (옵션)
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public void getRealEstateTradingArea(int page, int perPage,
                                               String researchDate_gte, String researchDate_lte,
                                               String regionCd_eq, String dealObj_eq)throws IOException, ParseException {

        String path = "getRealEstateTradingArea";

        Map<String, String> parameters = Map.of(
                "page", String.valueOf(page),
                "perPage", String.valueOf(perPage),
                "serviceKey",this.apiKey,
                URLEncoder.encode("cond[RESEARCH_DATE::GTE]","UTF-8"),researchDate_gte,
                URLEncoder.encode("cond[RESEARCH_DATE::LTE]","UTF-8"),researchDate_lte,
                URLEncoder.encode("cond[REGION_CD::EQ]","UTF-8"),regionCd_eq,
                URLEncoder.encode("cond[DEAL_OBJ::EQ]","UTF-8"),dealObj_eq
        );


        HTTPrequest.responseJSON(serviceDomain, path, parameters);


    }

}
