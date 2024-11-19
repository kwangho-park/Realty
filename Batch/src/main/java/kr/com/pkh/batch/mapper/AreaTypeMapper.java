package kr.com.pkh.batch.mapper;

import kr.com.pkh.batch.dto.db.AreaTypeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface AreaTypeMapper {
    ArrayList<String> selectTargetList();
}
