package kr.com.pkh.batch.openAPI.data;


import kr.com.pkh.batch.util.PropertiesUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;



/**
 * service name : 국토교통부 건축물대장정보 서비스
 * description : 건축물대장에서 관리하고 있는 총괄표제부, 표제부, 층별개요, 부속지번, 전유공용면적,
 * 오수정화시설, 주택가격, 전유부, 지역지구구역 등 속성정보(대용량 원시DB)
 *
 * issue :  @TestPropertySource, @ActiveProfiles 원인불명의 이유로 application.properties 를 탐색하지못하여 ClassPathResource 로 파일 탐색
 */
//@TestPropertySource("classpath:application.properties")   // src/main/java/resource/application.properties 경로의 파일 사용을 명시적으로 지정
//@ActiveProfiles                   // application.properties 에서 데이터를 조회 (path : src/test/resources/application.properties)
//@SpringBatchTest                  // spring batch 작업관련 bean만 설정하기때문에 job 을 테스트 할 수 있음
@SpringBootTest                     // spring 기반 어플리케이션의 모든 bean과 설정을 포함함
public class BldRgstServiceTest {


    @Test
    public void getBrExposPubuseAreaInfoTest() throws Exception {


        String apiKeyEncoding = PropertiesUtil.getProperty("publicDataPotal.openApi.apiKey.encoding");


        // given
        BldRgstService bldRgstService = new BldRgstService(apiKeyEncoding);

        String numOfRows="10";
        String pageNo ="1";
        String sigunguCd ="41192";       // 시군구코드(5자리) (필수) : 41192 (경기도 부천시 원미구)
        String bjdongCd ="10800";        // 법정동코드(5자리)  (필수) : 10800 (중동)
        String platGbCd="0";            // 코지구분 (1자리) : 0
        String bun="1051";              // 본번 (4자리) : 1051  (10510000 설악마을아파트 지번)
        String ji="0000";               // 지번 (4자리) : 0000
        String dongNm="310";               // 301 동 = 공급면적 59, 310 동 = 공급면적 69
        String hoNm="";
        String startDate="";            // ?
        String endDate ="";             // ?

        // when
        bldRgstService.getBrExposPubuseAreaInfo(numOfRows, pageNo, sigunguCd,bjdongCd,platGbCd, bun,ji,
                dongNm, hoNm, startDate, endDate);

        // then

    }

}
