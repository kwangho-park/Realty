package kr.com.pkh.batch.step.chunk.writer;

import kr.com.pkh.batch.dao.AptBuildingDAO;
import kr.com.pkh.batch.dao.AreaTypeDAO;
import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CollectGpsWriter implements ItemWriter<List<AptBuildingDTO>> {


    @Autowired
    public AptBuildingDAO aptBuildingDAO;

    @Override
    public void write(List<? extends List<AptBuildingDTO>> list) throws Exception {
        for(List<AptBuildingDTO> aptList : list) {
            for(AptBuildingDTO item : aptList) {
                aptBuildingDAO.updateAptBuildingGPS(item);
            }
        }
    }
}
