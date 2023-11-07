package kr.com.pkh.batch.openAPI;

import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.util.json.parser.JSONParser;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 서비스 명 : 부동산 거래현황 통계 조회 서비스 (RealEstateTradingSvc)
 * 인증인가 방식 : API key
 * http method : GET 방식만 지원
 *
 * basic uri : https://api.odcloud.kr/api/RealEstateTradingSvc/v1/상세서비스명?page=1&perPage=10&serviceKey=서비스키
 *
 */
@Slf4j
@Component
public class RealEstateTradingSvc {


    /**
     *
     * @Description 기본 parameter (page, perPage) 는 소문자이며, 이외 parameter 는 모두 대문자로 표기 <br>
     * @Description 기본 이외 parameter 는 특정 형식으로 URL encoding (UTF-8) 필요 <br>
     *
     * @Param APIkey : 공공 데이터 포털에서 발급받은 API key (encoding key)
     * @Param method
     * @Param path : 기능
     * @Return : json string 데이터 반환
     *
     * @throws IOException
     * @throws ParseException
     */
    public JSONObject requestAPI(String method, String path, Map<String, String> parameters) throws IOException, ParseException {

        int responseCode = 0;                 // http status code
        String responseMsg = "";              // http message

        String result = "";                   // http response code
        String message = "";                  // http response message

        JSONObject jsonObject=null;


        String targetUrl = "https://api.odcloud.kr/api/RealEstateTradingSvc/v1";


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

            log.info("request URI : "+ urlBuilder.toString());

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

            log.info("responseCode : " + responseCode);
            log.info("responseMsg : " + responseMsg);


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

                log.info("[success] response : " + response);


                JSONParser parser = new JSONParser();
                Object obj = parser.parse(response);

                jsonObject = (JSONObject) obj;

                log.info("[END] request API : "+path);
                return jsonObject;
            }else{

                log.info("[END] request API : "+path);
                return jsonObject;      // null
            }


        }catch(ConnectException e){
            log.info("[Exception] 서버 접속 실패");
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();

        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

}
