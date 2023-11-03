package kr.com.pkh.batch.extend.job.standard;

import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.extend.job.BatchTestConfig;
import kr.com.pkh.batch.util.json.parser.JSONParser;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Map;

/**
 * Batch job 기본 테스트 코드 (정상)
 * 테스트 대상 job : StandardConfig.class
 * 참고 : 하나의 테스트 코드는 하나의 job 테스트 가능
 */
// @Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardConfig.class})        // 환경설정 config , test target config
class StandardConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Value("${publicData.openApi.apiKey}")
    private String apiKey;

    @Test
    public void success() throws Exception {

        // given

        // when
        // StandardConfig 의 job 을 실행
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
    }



    /**
     * 공공 데이터포탈 openAPI 테스트
     * 인증인가 방식 : API key
     * 서비스 명 : 부동산 거래현황 통계 조회 서비스
     * 기본 uri : https://api.odcloud.kr/api/RealEstateTradingSvc/v1/상세서비스명?page=1&perPage=10&serviceKey=서비스키
     *
     */
    @Test
    public void putSetting() throws IOException, ParseException {

        // given //
        String method = "GET";
        String path = "getRealEstateTradingArea";       // 부동산 거래면적 조회

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
         JSONObject jsonObject = requestWebAPI(method, path, parameters);

        // then //
//        String result = (String) jsonObject.get("result");
//        String message = (String) jsonObject.get("message");
//        String data = (String) jsonObject.get("data");
//
//        System.out.println("result : " + result);
//        System.out.println("message : " + message);


    }




    /**
     *
     * @Param APIkey : 공공 데이터 포털에서 발급받은 API key (encoding key)
     * @Param method
     * @Param path : 기능
     *
     * @Return : json string 데이터 반환
     *
     * [openAPI 테스트 참고사항]
     * @Description 기본 parameter (page, perPage) 는 소문자이며, 이외 parameter 는 모두 대문자로 표기 <br>
     * @Description 기본 이외 parameter 는 특정 형식으로 URL encoding (UTF-8) 필요 <br>
     *
     */
    public JSONObject requestWebAPI(String method, String path, Map<String, String> parameters) throws IOException, ParseException {

        int responseCode = 0;                 // http status code
        String responseMsg = "";              // http message

        String result = "";                   // http response code
        String message = "";                  // http response message

        JSONObject jsonObject=null;


        String targetUrl = "https://api.odcloud.kr/api/RealEstateTradingSvc/v1";    // 부동산 거래현황 통계 조회 서비스


        try {

            // Create for HTTP get method URI //
            StringBuilder urlBuilder = new StringBuilder(targetUrl+"/"+path);
            urlBuilder.append("?");

            for(Map.Entry<String, String> entry : parameters.entrySet()){
                urlBuilder.append(entry.getKey());
                urlBuilder.append("=");
                urlBuilder.append(entry.getValue());
                urlBuilder.append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length()-1);     // 마지막 & 제거

            System.out.println("request URI : "+ urlBuilder.toString());

            URL url = new URL(urlBuilder.toString());


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();     // https 를 요청하는경우 인증서 검증 비활성화 설정 필요

            // http header 설정 //
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            //outputStream.writeBytes(json);
            outputStream.flush();
            outputStream.close();

            responseCode = connection.getResponseCode();
            responseMsg = connection.getResponseMessage();

            System.out.println("responseCode : " + responseCode);
            System.out.println("responseMsg : " + responseMsg);


            if (responseCode == HttpURLConnection.HTTP_OK) {


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String inputLine = "";


                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
                bufferedReader.close();

                // response 결과 //
                String response = stringBuffer.toString();

                System.out.println("[success] response : " + response);


                JSONParser parser = new JSONParser();
                Object obj = parser.parse(response);

                jsonObject = (JSONObject) obj;

                System.out.println("[END] request API : "+path);
                return jsonObject;

//                result = (String) jsonObj.get("result");
//                message = (String) jsonObj.get("message");

            }

            System.out.println("[END] request API : "+path);


        }catch(ConnectException e){
            System.out.println("[Exception] 서버 접속 실패");
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();

        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }



}