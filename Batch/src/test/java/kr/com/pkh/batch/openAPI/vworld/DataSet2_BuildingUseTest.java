package kr.com.pkh.batch.openAPI.vworld;


import kr.com.pkh.batch.openAPI.vworld.service.DataSet2_BuildingUse;
import kr.com.pkh.batch.util.PropertiesUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataSet2_BuildingUseTest {


    @Test
    public void getBuildingUse() throws  Exception{


        // given

        DataSet2_BuildingUse buildingUse = new DataSet2_BuildingUse();
        String pnu="4119010800110510000";   // 경기도 부천시 중동 (4119010800) + 일반 (1) + 설악마을아파트지번 {본번 (1051) + 부번 없음 (0000)}
                                            // 301 ~ 311 동까지 건물정보 조회
        String mainPrposCode="";
        String detailPrposCode="";
        String format="json";
        String numOfRows="1";
        String pageNo="1";
        String domain="www.realty.co.kr";
        String key = PropertiesUtil.getProperty("vworld.openApi.apikey");


        // when
//        buildingUse.getBuildingUse(pnu, mainPrposCode,detailPrposCode , format, numOfRows, pageNo,key,domain);

        buildingUse.getBuildingUse(pnu);


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
        String key = PropertiesUtil.getProperty("vworld.openApi.apikey");
        String domain= "";

        buildingUse.getBuildingUseWFS(typename, bbox, pnu, maxFeatures, resultType, srsName, key
        , domain);

        // then


    }


}
