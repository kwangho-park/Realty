package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.AptTradeDTO;
import kr.com.pkh.batch.dto.AptTradeEntity;
import kr.com.pkh.batch.dto.TradeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
public class AptTradeProcessor implements ItemProcessor<TradeDTO, List<AptTradeEntity>> {

    @Override
    public List<AptTradeEntity> process(TradeDTO tradeDTO) {

        List<AptTradeEntity> aptTradeEntityList = new ArrayList<AptTradeEntity>();
        log.info("[process] START");
        try{


            // 주소 -> GPS 좌표 변환하여 TB_APT_TRADE 테이블에 추가 예정
            List<AptTradeDTO> aptTradeList = new ArrayList<>();
            aptTradeList = tradeDTO.getAptTradeDTOList();

            // 반복문으로 아파트이름을 Entity 리스트에 저장
            Random random = new Random();
            int testId = 0;                 // id
            Long testPnu = null;            // pnu
            String name = "";               // apt name

            // 결과값이 존재하지 않는 경우
            if(aptTradeList==null){
                return null;
            }
            for(int loop=0;loop<aptTradeList.size();loop++){

                AptTradeEntity aptTradeEntity = new AptTradeEntity();
                testId = random.nextInt(100);
                testPnu = random.nextLong();
                log.info("[process] name : "+aptTradeList.get(loop).getName());

                aptTradeEntity.setId(aptTradeList.get(loop).getId());
                aptTradeEntity.setPnu(aptTradeList.get(loop).getPnu());
                aptTradeEntity.setName(aptTradeList.get(loop).getName());
                aptTradeEntity.setTradeAmount(aptTradeList.get(loop).getTradeAmount());
                aptTradeEntity.setTradeDate(aptTradeList.get(loop).getTradeDate());
                aptTradeEntity.setInsertDateTime(LocalDateTime.now());
                aptTradeEntityList.add(aptTradeEntity);
            }


        }catch(Exception e){
            e.printStackTrace();
        }

        log.info("[process] END");
        return aptTradeEntityList;

    }
}
