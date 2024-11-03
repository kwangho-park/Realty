package kr.com.pkh.batch.step.chunk.writer;

import kr.com.pkh.batch.dto.db.AptTradeDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AptAddressWriter implements ItemWriter<AptTradeDTO> {


    // original
//    private AptTradeRepository repository;


    // original
//    @Autowired
//    public AptAddressWriter(AptTradeRepository repository){
//        this.repository = repository;
//    }

    /// original
    /**
     * tb_apt_trade 의 pk (id) 기준으로 테이블에 데이터가 존재하지 않는 경우 save, 존재하는경우 update
     * @param items items to be written
     */
//    @Override
//    public void write(List<? extends AptTradeEntity> items) throws Exception {
//        log.info("item@#@####item@#@##### {}" , items.size());
//        for(AptTradeEntity item : items) {
//            log.info("c " + item.getId());
//            if(item.getId() != null) {
//                repository.save(item);
//            }
//            //repository.save(item);
//        }
//
//    }

    // test
    /**
     * tb_apt_trade 의 pk (id) 기준으로 테이블에 데이터가 존재하지 않는 경우 save, 존재하는경우 update
     * @param items items to be written
     */
    @Override
    public void write(List<? extends AptTradeDTO> items) throws Exception {
        log.info("item@#@####item@#@##### {}" , items.size());

        for(AptTradeDTO item : items) {
            log.info("c " + item.getId());

            if(item.getId() != null) {
//                repository.save(item);
            }
            //repository.save(item);
        }

    }


}

