package kr.com.pkh.batch.dto.api;

/**
 * 공공데이터 포털의 건물정보 조회 API를 저장하는 DTO
 * openAPI : BldRgstService_v2/getBrExposPubuseAreaInfo
 *
 */
public class PubuseAreaDTO {


    String publicArea;  // 공용면적
    String privateArea; // 전용면적
    String mgmBldrgstPk;    // 관리 건축물 대장 (세대별 고유값)
    // 공급면적  ?
    // pnu ?


}
