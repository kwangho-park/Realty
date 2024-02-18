package kr.com.pkh.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 지역코드 테이블
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="tb_region_code")
public class RegionCodeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 생성을 DB에 위임 (by mysql DB)
    @Id
    @Column(name="RC_ID",length=11)
    private int id;

    @Column(name="RC_LOCATADD_NAME",length=45)
    private String locatadoName;
    @Column(name="RC_REGION_CODE",length=20)
    private long regionCode;
    @Column(name="RC_SIDO_CODE",length=2)
    private int sidoCode;
    @Column(name="RC_SGG_CODE",length=3)
    private int sggCode;
    @Column(name="RC_UMD_CODE",length=3)
    private int umdCode;
    @Column(name="RC_RI_CODE",length=2)
    private int riCode;
    @Column(name="RC_LOCATJUMIN_CODE",length=45)
    private String locatjuminCode;
    @Column(name="RC_LOCATJIJUK_CODE",length=45)
    private String locatjijukCode;
    @Column(name="RC_LOCAT_ORDER",length=45)
    private String locatOrder;
    @Column(name="RC_LOCATE_RM",length=45)
    private String locateRm;
    @Column(name="RC_LOCATHIGH_CODE",length=45)
    private String LocathighCode;
    @Column(name="RC_LOCALLOW_NAME",length=45)
    private String locallowName;
    @Column(name="RC_INSERT_DATETIME",length=50)
    private LocalDateTime insertDateTime;


}
