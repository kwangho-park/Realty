package kr.com.pkh.batch.dto.db;

import lombok.Data;

@Data
public class AreaTypeDTO {
    int num;                // index
    String privateArea;     // 전용면적
    String publicArea;      // 공용면적
    String supplyArea;      // 공급면적
    String tradeArea;       // 계약면적

    String abPnu;              // FK (TB_APT_BUILDING 의 FK)
}
