package kr.com.pkh.batch.dto;

import lombok.Data;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

@Data
public class TradeDTO {

    List<AptTradeDTO> aptTradeDTOList ;
    PageDTO pageDTO;

}
