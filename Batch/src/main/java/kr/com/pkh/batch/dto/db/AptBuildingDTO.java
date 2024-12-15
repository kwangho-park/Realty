package kr.com.pkh.batch.dto.db;

import lombok.Data;

/**
 * 아파트 명, 주소, 좌표를 담는 DTO
 */
@Data
public class AptBuildingDTO {

    String id="";           // 매매거래 일련번호
    String pnu = "";           // pnu 아파트단지 고유값
    int tradeAmount=0;      // 매매가격
    String tradeDate="";    //

    String insertDateTime = "";
    String address;
    String name;
    String gps;
}
