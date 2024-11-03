package kr.com.pkh.batch.step.chunk.writer;

import kr.com.pkh.batch.dao.AreaTypeDAO;
import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import org.springframework.batch.item.ItemWriter;

@Slf4j
@Component
public class AreaTypeWriter implements ItemWriter<List<AreaTypeDTO>> {


    @Autowired
    public AreaTypeDAO areaTypeDAO;

    @Override
    public void write(List<? extends List<AreaTypeDTO>> items){

    }


}
