package kr.com.pkh.batch.mapper;

import kr.com.pkh.batch.dto.AptTradeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AptTradeMapper {
    ArrayList<AptTradeDTO> selectTradeList();

    void insertAptTrade(AptTradeDTO aptTradeDTO);

    List<AptTradeDTO> selectAddressIsNull();
}