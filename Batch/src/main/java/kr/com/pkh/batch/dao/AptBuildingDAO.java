package kr.com.pkh.batch.dao;

import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.mapper.AptBuildingMapper;
import kr.com.pkh.batch.mapper.AptTradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AptBuildingDAO {

    @Autowired
    AptBuildingMapper aptBuildingMapper;

    public int insertAptAddress(AptBuildingDTO aptBuildingDTO) throws Exception {
        return aptBuildingMapper.insertAptAddress(aptBuildingDTO);
    }

    public List<AptBuildingDTO> selectBuildingAddressGpsNull() throws Exception {
        return aptBuildingMapper.selectBuildingAddressGpsNull();
    }

    public int updateAptBuildingGPS(AptBuildingDTO item) throws Exception {
        return aptBuildingMapper.updateAptBuildingGPS(item);
    }
}
