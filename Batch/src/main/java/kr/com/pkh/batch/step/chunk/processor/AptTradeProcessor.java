package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.db.AptTradeDTO;

import kr.com.pkh.batch.dto.api.TradePageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AptTradeProcessor implements ItemProcessor<TradePageDTO, List<AptTradeDTO>> {

    @Override
    public List<AptTradeDTO> process(TradePageDTO tradePageDTO) {

        log.info("[process] START");
        List<AptTradeDTO> aptTradeList = new ArrayList<>();

        try{

            aptTradeList = tradePageDTO.getAptTradeDTOList();

            // 결과값이 존재하지 않는 경우
            if(aptTradeList==null){
                return null;
            }


        }catch(Exception e){
            e.printStackTrace();
        }

        log.info("[process] END");
        return aptTradeList;

    }

}
