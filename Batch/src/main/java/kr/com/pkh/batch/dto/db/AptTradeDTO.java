package kr.com.pkh.batch.dto.db;

import lombok.Data;

/**
 * 공공 데이터 포털에서 조회한 아파트 매매 거래정보를 저장하는 DTO
 */
@Data
public class AptTradeDTO {

    String id="";           // 매매거래 일련번호
    String pnu = "";        // pnu 아파트단지 고유값
    int tradeAmount=0;      // 매매가격 (거래가격)
    String tradeDate="";    // 매매일자 (거래일자)

    String insertDateTime = "";

    String sigunCd; //시군구 코드
    String bjdCd;   //번정동 코드
    String platCd;  //대지구분코드
    String bunCd;   //본번코드
    String jiCd;    //지번코드


}
