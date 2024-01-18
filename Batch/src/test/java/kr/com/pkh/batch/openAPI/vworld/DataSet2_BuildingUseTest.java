package kr.com.pkh.batch.openAPI.vworld;


import kr.com.pkh.batch.BatchTestConfig;
import kr.com.pkh.batch.extend.job.standard.StandardConfig;
import kr.com.pkh.batch.openAPI.data.BuildingUseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.configuration.xml.ExceptionElementParser;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, StandardConfig.class})        // 환경설정 config , test target config
public class DataSet2_BuildingUseTest {

    @Value("${vworld.openApi.apikey}")
    private String key;

    @Test
    public void getBuildingUse() throws  Exception{

        // given
        DataSet2_BuildingUse buildingUse = new DataSet2_BuildingUse();
        String pnu="4119010800110510000";   // 경기도 부천시 중동 (4119010800) + 일반 (1) + 설악마을아파트지번 {본번 (1051) + 부번 없음 (0000)}
                                            // 301 ~ 311 동까지 건물정보 조회
        String mainPrposCode="";
        String detailPrposCode="";
        String format="json";
        String numOfRows="";
        String pageNo="";
        String domain="";
        String key=this.key;

        // when
        buildingUse.getBuildingUse(pnu, mainPrposCode,detailPrposCode , format, numOfRows, pageNo,key,domain);

        // then
    }

    @Test
    public void getBuildingUseWFS() throws Exception{

        // given
        DataSet2_BuildingUse buildingUse = new DataSet2_BuildingUse();

        // when
        String typename= "";
        String bbox= "37.5074,126.7694,37.509,126.7722,EPSG:4326";      // ymin,xmin,ymax,xmax (좌측 하단, 우측 상단)
                                                                        // 설악마을 주공아파트 : 37.508146 (위도) , 126.770690 (경도)

        String pnu= "";
        String maxFeatures= "";
        String resultType= "";
        String srsName= "EPSG:4326";
        String key= this.key;
        String domain= "";

        buildingUse.getBuildingUseWFS(typename, bbox, pnu, maxFeatures, resultType, srsName, key
        , domain);

        // then


    }


}
