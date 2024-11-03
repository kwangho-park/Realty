package kr.com.pkh.batch.dto.db;

import lombok.Data;

/**
 * 공공 데이터 포털에서 조회한 아파트 매매 거래정보를 저장하는 DTO
 */
@Data
public class AptTradeDTO {

    String id="";           // 매매거래 일련번호
    long pnu = 0;           // pnu 아파트단지 고유값
    String name="";         // 아파트명
    int tradeAmount=0;      // 매매가격
    String tradeDate="";    //

    String insertDateTime = "";
    String address;         // 주소

}
