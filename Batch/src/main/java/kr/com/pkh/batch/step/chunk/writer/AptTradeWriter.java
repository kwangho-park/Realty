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

    /**
     * tb_apt_trade 의 pk (id) 기준으로 테이블에 데이터가 존재하지 않는 경우 save, 존재하는경우 update
     * @param items items to be written
     */
    @Override
    public void write(List<? extends List<AptTradeEntity>> items) {
        log.info("[write] START");
        try{
            log.info("aptTrade wirter dfadfasdfsaf:: " + items.size());
            for(List<AptTradeEntity> chunk : items){

                for(AptTradeEntity item: chunk){
                    repository.save((AptTradeEntity) item);
                    log.info("[batch-writer] id "+item.getId() +"/ pnu : "+item.getPnu() +" / name : "+item.getName() );
                    log.info("[item address] {} ", item.getAddress());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        log.info("[write] END");
    }

}

