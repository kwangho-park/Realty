package kr.com.pkh.batch.step.chunk.writer;

import kr.com.pkh.batch.dao.AptTradeRepository;
import kr.com.pkh.batch.dto.AptTradeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class AptTradeWriter implements ItemWriter<List<AptTradeEntity>> {


    private AptTradeRepository repository;

    @Autowired
    public AptTradeWriter(AptTradeRepository repository){
        this.repository = repository;
    }

    @Override
    public void write(List<? extends List<AptTradeEntity>> items) throws Exception {

        for(List<AptTradeEntity> chunk : items){

            for(AptTradeEntity item: chunk){
                repository.save((AptTradeEntity) item);
                log.info("[batch-writer] id "+item.getId() +"/ pnu : "+item.getPnu() +" / name : "+item.getName() );
            }
        }
    }

}

