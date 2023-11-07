package kr.com.pkh.batch.openAPI;

import org.w3c.dom.Document;
import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.util.json.parser.JSONParser;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 서비스 명 : 아파트 매매 신고 데이터 조회 서비스 (Apartment Transaction Data)
 * 인증인가 방식 : API key
 * http method : GET 방식만 지원
 *
 * basic uri : http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade
 *
 *
 */
@Slf4j
@Component
public class AptTransactionSvc {


    public JSONObject requestAPI(String method, String path, Map<String, String> parameters) throws IOException, ParseException {

        int responseCode = 0;                 // http status code
        String responseMsg = "";              // http message

        String result = "";                   // http response code
        String message = "";                  // http response message

        JSONObject jsonObject=null;


        String targetUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade";


        try {

            // Create for HTTP get method URI //
            StringBuilder urlBuilder = new StringBuilder(targetUrl);
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


//            if (responseCode == HttpURLConnection.HTTP_OK) {


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String inputLine = "";


                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
                bufferedReader.close();

                // response 결과 //
                String responseXml = stringBuffer.toString();

                log.info("[success] response : " + responseXml);

                // http response 파싱작업 (XML -> java object) //

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                Document document = builder.parse(responseXml);

                Element root = document.getDocumentElement();

                NodeList nodeList = root.getElementsByTagName("header");

                for(int loop=0;loop<nodeList.getLength();loop++){
                    Element element = (Element) nodeList.item(loop);
                    String value = element.getTextContent();
                    log.info(element +":" +value);
                }

                log.info("[END] success request API : "+path);
                return jsonObject;
//            }else{
//
//                log.info("[END] failed request API : "+path);
//                return jsonObject;      // null
//            }


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
