package kr.com.pkh.batch.dto.api;

import kr.com.pkh.batch.dto.db.AptTradeDTO;
import kr.com.pkh.batch.dto.db.PageDTO;
import lombok.Data;
import java.util.List;

@Data
public class TradePageDTO {

    List<AptTradeDTO> aptTradeDTOList ;
    PageDTO pageDTO;        // openAPI를 사용하여 매매데이터를 수집하는 단위

}
