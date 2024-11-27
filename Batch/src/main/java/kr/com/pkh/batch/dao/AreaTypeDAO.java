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

    /**
     *  아파트 정보 테이블에 pnu가 존재하지않는 매매테이블의 (tb_apt_trade)의 pnu 리스트 조회
     *
     * @return
     * @throws Exception
     */
    public ArrayList<String> selectAreaTypeList() throws Exception{
        return areaTypeMapper.selectTargetList();
    }

}
