package kr.com.pkh.batch.dto;

import lombok.Data;

/**
 * 공공 데이터 포털에서 조회한 아파트 매매 거래정보를 저장하는 DTO
 */
@Data
public class AptTradeDTO {

    String id="";
    long pnu = 0;
    String name="";
    int tradeAmount=0;
    String tradeDatetime="";


}
