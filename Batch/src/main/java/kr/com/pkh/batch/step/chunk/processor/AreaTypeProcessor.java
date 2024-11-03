package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.batch.item.ItemProcessor;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AreaTypeProcessor implements ItemProcessor<AreaTypeDTO, List<AreaTypeDTO>> {

    @Override
    public List<AreaTypeDTO> process(AreaTypeDTO areaTypeDTO){
        List<AreaTypeDTO> areaTypeList = new ArrayList<>();

        ////// pnu 타입 변경 이후 진행 예정임 (bigint -> varchar(20))

        return areaTypeList;
    }

}
