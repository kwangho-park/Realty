package kr.com.pkh.realty.service;

import kr.com.pkh.realty.dao.AptBuildingMapper;
import kr.com.pkh.realty.dto.db.AptBuildingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AptBuildingService {

    @Autowired
    AptBuildingMapper aptBuildingMapper;

    public List<AptBuildingDTO> getAptBuildingGpsList() {
        return aptBuildingMapper.selectAptBuildingGpsList();
    }
}
