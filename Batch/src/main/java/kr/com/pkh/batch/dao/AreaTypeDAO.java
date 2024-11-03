package kr.com.pkh.batch.dao;

import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import kr.com.pkh.batch.mapper.AreaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AreaTypeDAO {

    @Autowired
    AreaTypeMapper areaTypeMapper;

    public ArrayList<Long> selectAreaTypeList() throws Exception{
        return areaTypeMapper.selectTargetList();
    }

}
