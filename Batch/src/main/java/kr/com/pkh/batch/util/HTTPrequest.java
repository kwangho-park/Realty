package kr.com.pkh.batch.util;

import kr.com.pkh.batch.util.json.JSONObject;
import kr.com.pkh.batch.util.json.parser.JSONParser;
import kr.com.pkh.batch.util.json.parser.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * http request 를 위한 클래스 (method : GET )
 */
@Slf4j
@Component
public class HTTPrequest {

    /**
     * @param serviceDomain web api service domain
     * @param path          web api path
     * @param parameters    web api parameter
     * @return JSONObject web api 반환 데이터 json을 java json format 으로 반환
     *
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject responseJSON(String serviceDomain, String path, Map<String, String> parameters) throws IOException, ParseException {

        int responseCode = 0;                 // http status code
        String responseMsg = "";              // http message

        String result = "";                   // http response code
        String message = "";                  // http response message

        JSONObject jsonObject=null;

        try {

            // Create for HTTP get method URI //
            StringBuilder urlBuilder = new StringBuilder(serviceDomain+"/"+path);
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

            // http header 설정 (GET) //
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

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
            log.info("[Exception] "+serviceDomain+" 서버 접속 실패");
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();

        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;

    }   // responseJSON() END


    /**
     *
     * @param serviceDomain web api service domain
     * @param path          web api path
     * @param parameters    web api parameter
     * @return JSONObject web api 반환 데이터 XML을  java json format 으로 파싱하여 반환
     *
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject responseXML(String serviceDomain, String servicePort, String commonPath, String path, Map<String, String> parameters) throws IOException, ParseException {

        int responseCode = 0;                 // http status code
        String responseMsg = "";              // http message

        String result = "";                   // http response code
        String message = "";                  // http response message

        JSONObject jsonObject=null;


        try {

            // Create for HTTP get method URI //
            StringBuilder urlBuilder = new StringBuilder(serviceDomain+servicePort+commonPath+path);
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

            // http header 설정 및 GET 요청//
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "text/xml");

            responseCode = connection.getResponseCode();
            responseMsg = connection.getResponseMessage();

            log.info("HTTP status code : " + responseCode);
            log.info("HTTP status msg : " + responseMsg);

            if (responseCode == HttpURLConnection.HTTP_OK) {


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

                // http response XML 출력 (향후 parsing 예정)   //
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder = factory.newDocumentBuilder();
//
//                Document doc = builder.parse(new InputSource(new StringReader(responseXml)));
//
//                NodeList nodeList = doc.getElementsByTagName("*");
//
//
//                for (int i = 0; i < nodeList.getLength(); i++) {
//                    Element element = (Element) nodeList.item(i);
//                    log.info(element.getNodeName() + ": " + element.getTextContent());
//                }

                log.info("[END] success request API : "+path);
                return jsonObject;

            }else{

                log.info("[END] failed request API : "+path);
                return jsonObject;      // null
            }


        }catch(ConnectException e){
            log.info("[Exception] "+serviceDomain+" 서버 접속 실패");
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();

        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }



}

