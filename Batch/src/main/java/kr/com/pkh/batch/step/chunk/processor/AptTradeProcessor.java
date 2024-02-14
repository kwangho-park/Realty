package kr.com.pkh.batch.step.chunk.processor;

import kr.com.pkh.batch.dto.AptTradeDTO;
import kr.com.pkh.batch.dto.AptTradeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
public class AptTradeProcessor implements ItemProcessor<List<AptTradeDTO>, List<AptTradeEntity>> {

    @Override
    public List<AptTradeEntity>  process(List<AptTradeDTO> aptTradeList) throws Exception {

        List<AptTradeEntity> aptTradeEntityList = new ArrayList<AptTradeEntity>();


        // 반복문으로 아파트이름을 Entity 리스트에 저장
        Random random = new Random();
        int testId = 0;                 // id
        Long testPnu = null;            // pnu
        String name = "";               // apt name

        for(int loop=0;loop<aptTradeList.size();loop++){

            AptTradeEntity aptTradeEntity = new AptTradeEntity();
            testId = random.nextInt(100);
            testPnu = random.nextLong();

            aptTradeEntity.setId(aptTradeList.get(loop).getId());
            aptTradeEntity.setPnu(aptTradeList.get(loop).getPnu());
            aptTradeEntity.setName(aptTradeList.get(loop).getName());
            aptTradeEntity.setTradeAmount(aptTradeList.get(loop).getTradeAmount());
            aptTradeEntity.setTradeDateTime(aptTradeList.get(loop).getTradeDatetime());

            aptTradeEntityList.add(aptTradeEntity);
        }


        return aptTradeEntityList;
    }
}
