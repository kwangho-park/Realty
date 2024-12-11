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

            ////// '전용면적' 을 기준으로 아파트별 타입의 중복을 제거하고 , 공급면적을 연산하여 AreaTypeDTO 에 저장하여 인서트 데이터 생성
            // (아파트 단지내에 전용면적이 동일하더라도 공용면적이 다른 경우가 있음, 최초 수집된 데이터를 기준으로 공용면적을 계산함)


        }catch(Exception e){
            e.printStackTrace();
        }

        return areaTypeList;






    }

}
