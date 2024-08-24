package kr.com.pkh.batch.dto;

import lombok.Data;

@Data
public class RegionCodeDTO {
    int id;                 // PK : auto increment
    String locataddName;    // 지역주소명
    Long regionCode;         // 지역코드(10자리) index
    int sidoCode;           // 시도코드 (2자리) index
    int sggCode;            // 시군구코드 (3자리) index

    int umdCode;            // 읍면동 코드 (3자리) index
    int riCode;             // 리 코드 (2자리) index
    String locatjuminCode;  // 지역코드_주민
    String locatjijukCode;  // 지역코드_지적
    String locatOrder;      // 서열

    String locateRm;        // 비고
    String locathighCode;   // 상위지역코드
    String locallowName;    // 하위지역명
    String insertDatetime;  // 데이터 생성일자


}
