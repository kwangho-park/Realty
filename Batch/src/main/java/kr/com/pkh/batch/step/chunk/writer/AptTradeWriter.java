package kr.com.pkh.batch.step.chunk.writer;

import kr.com.pkh.batch.dao.AptTradeDAO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Slf4j
@Component
public class AptTradeWriter implements ItemWriter<List<AptTradeDTO>> {


    @Autowired
    public AptTradeDAO aptTradeDAO;


    /**
     * tb_apt_trade 의 pk (id) 기준으로 테이블에 데이터가 존재하지 않는 경우 save, 존재하는경우 update
     * @param items items to be written
     */
    @Override
    public void write(List<? extends List<AptTradeDTO>> items) {
        log.info("[write] START");

        try{
            log.info("[aptTrade writer] items size : " +items.size());

            for(List<AptTradeDTO> aptTradeList : items){

                for(AptTradeDTO aptTradeDTO:aptTradeList){
                    log.info("id (매매거래 일련번호) : "+ aptTradeDTO.getId());
                    aptTradeDAO.insertAptTrade(aptTradeDTO);
                }
            }



        }catch(Exception e){
            e.printStackTrace();
        }

        log.info("[write] END");
    }

}

