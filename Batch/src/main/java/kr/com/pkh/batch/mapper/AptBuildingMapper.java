package kr.com.pkh.batch.mapper;

import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AptBuildingMapper {

    int insertAptAddress(AptBuildingDTO aptBuildingDTO);
}
