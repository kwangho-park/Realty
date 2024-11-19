package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.batch.item.ItemProcessor;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AreaTypeProcessor implements ItemProcessor<List<String>, List<AreaTypeDTO>> {

    @Override
    public List<AreaTypeDTO> process(List<String> pnuList){

        List<AreaTypeDTO> areaTypeList = new ArrayList<>();     // 결과
        try{
            log.info("process START");

            for(String test:pnuList){
                log.info("pnu:"+test);
            }
            
            // [예정] BldRgstHubService openAPI 를 사용하여 전용면적, 공용면적을 수집하고 공급면적을 가공하여 areaTypeList에 저장

        }catch(Exception e){
            e.printStackTrace();
        }

        return areaTypeList;






    }

}
