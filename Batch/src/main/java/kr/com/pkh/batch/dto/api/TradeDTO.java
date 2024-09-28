package kr.com.pkh.batch.dto;

import lombok.Data;
import java.util.List;

@Data
public class TradeDTO {

    List<AptTradeDTO> aptTradeDTOList ;
    PageDTO pageDTO;        // openAPI를 사용하여 매매데이터를 수집하는 단위

}
