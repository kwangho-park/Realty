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

    @Override
    public List<AreaTypeDTO> process(List<String> pnuList){

        List<AreaTypeDTO> areaTypeList = new ArrayList<>();     // 결과



        try{
            log.info("process START");


            ////// 아파트별 타입의 중복을 제거하고 , 공급면적을 연산하여 AreaTypeDTO 에 저장하여 인서트 데이터 생성


        }catch(Exception e){
            e.printStackTrace();
        }

        return areaTypeList;






    }

}
