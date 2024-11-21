package kr.com.pkh.batch.dto.api;

import lombok.Data;

/**
 * 공공데이터 포털의 건물정보 조회 API를 저장하는 DTO
 * openAPI : BldRgstService_v2/getBrExposPubuseAreaInfo
 *
 */
@Data
public class PubuseAreaDTO {

    String publicArea;  // 공용면적
    String privateArea; // 전용면적
    String mgmBldrgstPk;    // 관리 건축물 대장 (세대별 고유값)
    String supplyArea;

    String pnu;


}
