package kr.com.pkh.batch.dto.db;

import lombok.Data;

@Data
public class AreaTypeDTO {

    private int num;                // sequence
    private float privateArea;     // 전용면적
    private float publicArea;      // 공용면적
    private float supplyArea;      // 공급면적
    private float tradeArea;       // 계약면적

    private String abPnu;              // FK (TB_APT_BUILDING 의 FK)
}
