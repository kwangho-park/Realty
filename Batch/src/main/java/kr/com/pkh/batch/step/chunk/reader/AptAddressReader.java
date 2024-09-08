package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dao.AptTradeDAO;
import kr.com.pkh.batch.dto.AptTradeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AptAddressReader implements ItemReader<List<AptTradeDTO>> {

    private AptTradeDAO aptTradeDAO;

    @Autowired
    public AptAddressReader(AptTradeDAO aptTradeDAO){
        this.aptTradeDAO=aptTradeDAO;
    }


    @Override
    public List<AptTradeDTO> read(){

        List<AptTradeDTO>  aptTradeList = null;

        try{
            aptTradeList = aptTradeDAO.selectAddressIsNull();

        }catch(Exception e){
            e.printStackTrace();
        }


        return aptTradeList;
    }

}
