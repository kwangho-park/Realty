package kr.com.pkh.realty.dao;

import kr.com.pkh.realty.dto.db.AptBuildingDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AptBuildingMapper {
    List<AptBuildingDTO> selectAptBuildingGpsList();
}
