package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import kr.com.pkh.batch.openAPI.data.service.BldRgstHubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.batch.item.ItemProcessor;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AreaTypeProcessor implements ItemProcessor<List<String>, List<AreaTypeDTO>> {

    @Value("${publicDataPotal.openApi.apiKey.encoding}")
    private String apiKey;

    @Autowired
    BldRgstHubService bldRgstHubService;

    @Override
    public List<AreaTypeDTO> process(List<String> pnuList){

        List<AreaTypeDTO> areaTypeList = new ArrayList<>();     // 결과

        String serviceKey = this.apiKey;
        String pageNo="1";
        String numOfRows="100";
        String sigunguCd ="41192";      // 시군구코드(5자리) (필수) : 41192 (경기도 부천시 원미구)
        String bjdongCd ="10800";       // 법정동코드(5자리)  (필수) : 10800 (중동)
        String platGbCd="0";            // 토지구분 (1자리) : 0
        String bun="1051";              // 본번 (4자리) : 1051  (10510000 설악마을아파트 지번)
        String ji="0000";               // 지번 (4자리) : 0000


        try{
            log.info("process START");

            for(String test:pnuList){
                log.info("pnu:"+test);
            }

            // pnu 기준으로 아파트 주소 및 아파트 면적타입 조회
            // [예정] BldRgstHubService openAPI 를 사용하여 전용면적, 공용면적을 수집
            // [예정] bldRgstHubService  서비스의 parser 구현 예정
            bldRgstHubService.getBrExposPubuseAreaInfo(
                    serviceKey,  pageNo,
                    numOfRows,  sigunguCd,
                    bjdongCd, platGbCd,
                    bun, ji);

            /////// 데이터 파싱

            //////  writer 에서 데이터인서트

            ////// 아파트 주소 정보도 writer 에서 인서트하는것으로 변경됨 (v-world 에서 pnu 로 수집되지않는 데이터가 있음을 확인함 = 경기도 부천시의 pnu로 주소데이터 조회되지않음)

        }catch(Exception e){
            e.printStackTrace();
        }

        return areaTypeList;






    }

}
