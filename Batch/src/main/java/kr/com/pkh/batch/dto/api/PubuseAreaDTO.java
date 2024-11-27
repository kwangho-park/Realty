package kr.com.pkh.batch.dto.api;

import lombok.Data;

/**
 * 공공데이터 포털의 건물정보 조회 API를 저장하는 DTO (=xml string 의 <item> 범위)
 *
 * openAPI : BldRgstService_v2/getBrExposPubuseAreaInfo
 *
 */
@Data
public class PubuseAreaDTO {

    String mgmBldrgstPk;    // 관리 건축물 대장 ([중요] 세대별 고유값)
    String privateArea;     // 전용면적
    String publicArea;      // 공용면적 (면적 타입별 n개의 공용면적이 존재할 수 있음)

    String bldNm;           // 아파트명
    String platPlc;         // 구주소
    String newPlatPlc;      // 도로명 주소
    String pnu;


}
