package kr.com.pkh.batch.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 공공 데이터 포털에서 조회한 아파트 매매 거래정보를 저장하는 DTO
 */
@Data
public class AptTradeDTO {

    Map<String, String> pnuMap = new HashMap<>();

    long pnu = 0;
    String name="";


}
