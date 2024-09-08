package kr.com.pkh.batch.openAPI.data;
import kr.com.pkh.batch.util.PropertiesUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

// 공공데이터 포털에서 해당 서비스가 종료되고 vworld로 이관됨
@SpringBootTest
public class BuildingUseServiceTest {


    @Test
    public void getBuildingUseWMS() throws Exception{

        String apiKey = PropertiesUtil.getProperty("publicDataPotal.openApi.apiKey.encoding");
        // given
        BuildingUseService buildingUseService = new BuildingUseService(apiKey);

        String layers = "F253";
        String crs = "EPSG:5174";
        String bbox1 = "217694,448235,218608,449094";       // lc1,lc2,uc1,uc2 (좌측 하단좌표 x,y 우측 상단좌표 x,y)
        String bbox2 = "217894,448235,218808,449094";       // 하단/상단의 x축 좌표를 200씩 증가시켰더니, 오른쪽으로 이동된 bit map 반환됨
        String width = "700";
        String height = "700";
        String format = "image/png";
        String transparent = "";
        String bgcolor = "";
        String exceptions = "";

        // when
        buildingUseService.getBuildingUseWMS(layers,crs,bbox1,width,height,format, transparent, bgcolor, exceptions);

        // then
    }

    @Test
    public void getBuildingUseWFS() throws  Exception{

        String apiKey = PropertiesUtil.getProperty("publicDataPotal.openApi.apiKey.encoding");

        // given
        BuildingUseService buildingUseService = new BuildingUseService(apiKey);
        String typeName = "";
        String bbox = "";
        String pnu1="4145210800103250001";   // 특정 건물 조회(max 19자리수) : 법정동(8-10) + 토지구분(1)+ 지번(본번4/부번4)
        String pnu2="41452108";              // 특정 지역의 건물목록 조회 : 법정동 (8)
        String pnu3="4119210800110510000";   // 경기도 부천시 원미구 중동 (4119210800) + 일반 (1) + 설악마을아파트지번 {본번 (1051) + 부번 없음 (0000)}
                                            // 301 ~ 311 동까지 건물정보 조회

        String maxFeatures = "";
        String srsName="";
        String resultType = "";

        // when
        buildingUseService.getBuildingUseWFS(typeName,bbox,pnu1,maxFeatures,srsName,resultType);

        // then
    }

    @Test
    public void getBuildingUse() throws  Exception{
        String apiKey = PropertiesUtil.getProperty("publicDataPotal.openApi.apiKey.encoding");
        // given
        BuildingUseService buildingUseService = new BuildingUseService(apiKey);
        String pnu="4119210800110510000";
        String mainPrposCode="";
        String detailPrposCode="";
        String format="";
        String numOfRows="";
        String pageNo="";

        // when
        buildingUseService.getBuildingUse(pnu, mainPrposCode,detailPrposCode , format, numOfRows, pageNo);
        // then
    }

}
