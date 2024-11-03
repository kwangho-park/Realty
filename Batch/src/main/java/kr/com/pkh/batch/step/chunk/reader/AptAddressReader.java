package kr.com.pkh.batch.step.chunk.reader;

import kr.com.pkh.batch.dao.AptTradeDAO;
import kr.com.pkh.batch.dto.db.AptTradeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class AptAddressReader implements ItemReader<AptTradeDTO> {

    private final AptTradeDAO aptTradeDAO;
    private List<AptTradeDTO> aptTradeDTOList;
    private int nextIndex = 0;

    @Autowired
    public AptAddressReader(AptTradeDAO aptTradeDAO) {
        this.aptTradeDAO = aptTradeDAO;
    }

    @PostConstruct
    public void init() throws Exception {
        log.info("주소정보 저장 로직 시작");
        aptTradeDTOList = aptTradeDAO.selectAddressIsNull(); // 여러 결과를 가져옴
    }

    @Override
    public AptTradeDTO read() {
        if (nextIndex < aptTradeDTOList.size()) {
            return aptTradeDTOList.get(nextIndex++);
        } else {
            return null; // 더 이상 읽을 데이터가 없을 때 null 반환
        }
    }
}
