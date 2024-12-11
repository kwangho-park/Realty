package kr.com.pkh.batch.dao;

import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.mapper.AptTradeMapper;
import org.springframework.batch.core.configuration.xml.ExceptionElementParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AptTradeDAO {

    @Autowired
    AptTradeMapper aptTradeMapper;

    public ArrayList<AptTradeDTO> selectTradeList() throws Exception {
        return aptTradeMapper.selectTradeList();

    }

    // 매매거래 일련번호가 존재하지 않는경우 데이터 인서트
    public void insertAptTrade(AptTradeDTO aptTradeDTO) throws Exception {
        aptTradeMapper.insertAptTrade(aptTradeDTO);
    }

    public List<AptTradeDTO> selectAddressIsNull () throws Exception{
        return aptTradeMapper.selectAddressIsNull();
    }

    public List<AptTradeDTO> selectPnuList () throws Exception{
        return aptTradeMapper.selectPnuList();
    }


}
