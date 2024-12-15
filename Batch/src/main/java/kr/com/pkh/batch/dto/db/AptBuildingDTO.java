package kr.com.pkh.batch.dto.db;

import lombok.Data;

/**
 * 아파트 주소, 좌표를 담는 DTO
 * table : tb_apt_building
 *
 */
@Data
public class AptBuildingDTO extends AreaTypeDTO{

    private String pnu = "";           // pnu 아파트단지 고유값
    private String name="";            // 아파트명
    private String address="";          // 구주소
    private String roadAddress="";      // 도로명주소
    private String gps="";              // GPS 좌표 (y,x; 위도경도) (format ex : 126.7692711,37.50877829)
    private int gis=0;                  // GIS 건물통합식별번호 (정수 20자리)

}
