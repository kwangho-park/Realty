package kr.com.pkh.batch.mapper;

import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface AreaTypeMapper {

    // 미사용
//    ArrayList<String> selectTargetList();

    int insertAreaType(AptBuildingDTO aptBuildingDTO);

}
