package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dao.AreaTypeDAO;
import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AreaTypeReader implements ItemReader<List<Long>> {

    private AreaTypeDAO areaTypeStepDAO;

    @Autowired
    public AreaTypeReader(AreaTypeDAO areaTypeStepDAO){
        this.areaTypeStepDAO=areaTypeStepDAO;
    }


    @Override
    public List<Long> read(){

        List<Long>  areaTypeList = new ArrayList<Long>();

        try{
            areaTypeList = areaTypeStepDAO.selectAreaTypeList();

        }catch(Exception e){
            e.printStackTrace();
        }


        return areaTypeList;
    }

}
