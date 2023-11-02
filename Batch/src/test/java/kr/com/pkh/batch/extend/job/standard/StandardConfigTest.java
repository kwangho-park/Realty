package kr.com.pkh.batch.extend.job.standard;

import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.extend.job.BatchTestConfig;
import kr.com.pkh.batch.util.json.parser.JSONParser;
import kr.com.pkh.batch.util.json.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Batch job 기본 테스트 코드 (정상)
 * 테스트 대상 job : StandardConfig.class
 * 참고 : 하나의 테스트 코드는 하나의 job 테스트 가능
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardConfig.class})        // 환경설정 config , test target config
class StandardConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

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
     * 서비스 명 : 부동산거래현황 통계 조회 서비스
     */
    @Test
    public void putSetting() throws IOException, ParseException {

        // given //
        String secretKey = "3MYH2UYDM73441C3PPN179F089R5Y27G";   // 32byte
        String secretIv = "87W1M4S8009730GK";                    // 16byte
        String systemId = "stealth";

        // 수정예정
        String json = "{"
                + "\"otp_scope\":\"20\","
                + "\"otp_length\":\"80\","
                + "\"systemId\":\"stealth\","
                + "\"systemPw\":\"stealth123\""
                + "}";
        String method = "GET";
        String path = "api/sys/settings";       //// 수정예정

        // when //
        JSONObject jsonObject = requestWebAPI(APIKey, json, method, path);

        // then //
        String result = (String) jsonObject.get("result");
        String message = (String) jsonObject.get("message");
        String data = (String) jsonObject.get("data");

        System.out.println("result : " + result);
        System.out.println("message : " + message);


    }




    /**
     *
     *
     *
     * @Param
     * @Param
     * @Param
     * @Return : json string 데이터 반환
     */
    public JSONObject requestWebAPI(String APIkey, String json, String method, String path) throws IOException, ParseException {

        int responseCode = 0;                 // http status code
        String responseMsg = "";              // http message

        String result = "";                   // http response code
        String message = "";                  // http response message

        JSONObject jsonObject=null;

        try {

            System.out.println("[HTTP body] jsonString : " + json);


            ///// URI 에 get method parameter 추가예정
            URL url = new URL("https://api.odcloud.kr/api/RealEstateTradingSvc/v1/getRealEstateTradingArea/"+path);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();     // https 를 요청하는경우 인증서 검증 비활성화 설정 필요

            // http header 설정 //
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            outputStream.writeBytes(json);
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

                System.out.println("[END] request API : hotp/"+path);
                return jsonObject;

//                result = (String) jsonObj.get("result");
//                message = (String) jsonObj.get("message");

            }

            System.out.println("[END] request API : hotp/"+path);


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