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

        // [review] 아파트 매매정보 수집 step (aptTradeStep) 이 실행되기 이전 시점에 실행되는 함수이기 때문에,
        // TB_APT_TRADE 에 데이터가 수집되지않은 상태에서 조회하는건 논리적 오류로 보임
        aptTradeDTOList = aptTradeDAO.selectAddressIsNull(); // 여러 결과를 가져옴

    }

    @Override
    public AptTradeDTO read() throws Exception {

        log.info("주소정보 저장 로직 시작");

        // [review]
         aptTradeDTOList = aptTradeDAO.selectAddressIsNull();

        if (nextIndex < aptTradeDTOList.size()) {
            return aptTradeDTOList.get(nextIndex++);
        } else {
            return null; // 더 이상 읽을 데이터가 없을 때 null 반환
        }
    }
}
