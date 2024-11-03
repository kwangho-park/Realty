package kr.com.pkh.batch.mapper;

import kr.com.pkh.batch.dto.db.AptTradeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AptTradeMapper {
    ArrayList<AptTradeDTO> selectTradeList();

    void insertAptTrade(AptTradeDTO aptTradeDTO);

    List<AptTradeDTO> selectAddressIsNull();

    int insertAptAddress(AptTradeDTO aptTradeDTO);
}
