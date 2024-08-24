package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.AptTradeDTO;

import kr.com.pkh.batch.dto.TradeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AptTradeProcessor implements ItemProcessor<TradeDTO, List<AptTradeDTO>> {

    @Override
    public List<AptTradeDTO> process(TradeDTO tradeDTO) {

        log.info("[process] START");
        List<AptTradeDTO> aptTradeList = new ArrayList<>();

        try{

            aptTradeList = tradeDTO.getAptTradeDTOList();

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
