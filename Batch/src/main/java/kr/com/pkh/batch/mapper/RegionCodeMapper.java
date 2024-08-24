package kr.com.pkh.batch.mapper;

import kr.com.pkh.batch.dto.AptTradeDTO;
import kr.com.pkh.batch.dto.RegionCodeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface RegionCodeMapper {
    ArrayList<RegionCodeDTO> selectRegionCodeList();

}
