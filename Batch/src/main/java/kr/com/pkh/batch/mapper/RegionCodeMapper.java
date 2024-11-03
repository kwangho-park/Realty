package kr.com.pkh.batch.mapper;

import kr.com.pkh.batch.dto.db.RegionCodeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface RegionCodeMapper {
    ArrayList<RegionCodeDTO> selectRegionCodeList();

}
