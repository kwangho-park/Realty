package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dao.AreaTypeDAO;
import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import kr.com.pkh.batch.openAPI.data.service.BldRgstHubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AreaTypeReader implements ItemReader<List<String>> {

    private int count =0;
    private AreaTypeDAO areaTypeStepDAO;

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    private BldRgstHubService bldRgstHubService;

    @Autowired
    public AreaTypeReader(AreaTypeDAO areaTypeStepDAO, BldRgstHubService bldRgstHubService){
        this.areaTypeStepDAO=areaTypeStepDAO;
        this.bldRgstHubService=bldRgstHubService;
    }

    public int getCount() {
        return this.count;
    }
    @Override
    public List<String> read() {


        String serviceKey = this.apiKey;
        String pageNo="1";
        String numOfRows="100";
        String sigunguCd ="41192";      // 시군구코드(5자리) (필수) : 41192 (경기도 부천시 원미구)
        String bjdongCd ="10800";       // 법정동코드(5자리)  (필수) : 10800 (중동)
        String platGbCd="0";            // 토지구분 (1자리) : 0
        String bun="1051";              // 본번 (4자리) : 1051  (10510000 설악마을아파트 지번)
        String ji="0000";               // 지번 (4자리) : 0000


        List<String>  pnuList = new ArrayList<String>();

        try{

            log.info("read START");
            pnuList = areaTypeStepDAO.selectAreaTypeList();

            // job 종료 지점설정 (1회 reader 실행 후 collectAreaTypeJob 을 종료)
            if(count==1){
                return null;
            }

            count++;
            for(String pnu:pnuList){
                log.info("pnu:"+pnu);
            }


            // pnu 기준으로 아파트 주소 및 아파트 면적타입 조회
            bldRgstHubService.getBrExposPubuseAreaInfo(
                    serviceKey,  pageNo,
                    numOfRows,  sigunguCd,
                    bjdongCd, platGbCd,
                    bun, ji);


        }catch(Exception e){
            e.printStackTrace();
        }


        return pnuList;
    }

}
