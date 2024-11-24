package kr.com.pkh.batch.step.chunk.writer;

import kr.com.pkh.batch.dao.AptBuildingDAO;
import kr.com.pkh.batch.dto.db.AptBuildingDTO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.dao.AptTradeDAO;
import kr.com.pkh.batch.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AptAddressWriter implements ItemWriter<AptBuildingDTO> {

    @Autowired
    AptBuildingDAO aptBuildingDAO;

    /**
     * tb_apt_trade 의 pk (id) 기준으로 테이블에 데이터가 존재하지 않는 경우 save, 존재하는경우 update
     * @param items items to be written
     */
    @Override
    public void write(List<? extends AptBuildingDTO> items) throws Exception {
        log.info("item@#@####item@#@##### {}" , items.size());

        for(AptBuildingDTO item : items) {
            log.info("c " + item.getPnu());
            log.info("address :: {} ", item.getAddress());

            if(!StringUtil.isEmpty(item.getPnu()) && !StringUtil.isEmpty(item.getAddress())) {

                // [review] aptTradeDAO는 TB_APT_TRADE 를 관리하는 DAO 이기 때문에,
                // TB_APT_BUILDING 을 관리하는 dao, mapper 를 생성해야할것으로 판단됨
                // 건물정보 테이블 insert
                log.info("item::::: {}" , item);
                aptBuildingDAO.insertAptAddress(item);

            }
            if(item.getId() != null) {
//                repository.save(item);        // legacy
            }
            //repository.save(item);            // legacy
        }

    }


}

