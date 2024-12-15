package kr.com.pkh.batch.dao;

import kr.com.pkh.batch.dto.db.RegionCodeDTO;
import kr.com.pkh.batch.mapper.RegionCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionCodeDAO {

    @Autowired
    RegionCodeMapper regionCodeMapper;

    // 시퀀스 기준으로 내림차순하여 전체 조회
    public List<RegionCodeDTO> selectRegionCodeList() throws Exception{
        return regionCodeMapper.selectRegionCodeList();
    }
}
