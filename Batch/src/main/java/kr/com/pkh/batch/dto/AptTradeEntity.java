package kr.com.pkh.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 부동산 매매 정보 테이블
 * jpa-> mybatis 전환으로 제거예정
 *
 */
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name="tb_apt_trade")
//@DynamicUpdate      // 변경되는 agr 만 수정하는 어노테이션
//public class AptTradeEntity {
//
//    //@GeneratedValue(strategy = GenerationType.IDENTITY) // pk 생성을 DB에 위임 (by mysql DB)
//    @Id
//    @Column(name="AT_ID", length = 20)
//    private String id;                     // 부동산거래계약신고 필증 일련번호 : ex 41190-11171
//
//    @Column(name="AT_PNU", length = 20)
//    private long pnu;                    // 필지고유번호 : 17~19자리 정수, 법정동(8-10) + 토지구분(1)+ 지번(본번4/부번4)
//
//    @Column(name="AT_NAME", length = 50)
//    private String name;                // 아파트 단지명
//
//    @Column(name="AT_TRADE_AMOUNT", length = 11)
//    private int tradeAmount;             // 매매가격
//
//    @Column(name="AT_TRADE_DATE", length = 50)
//    private String tradeDate;            // 거래일자
//
//    @Column(name="AT_INSERT_DATETIME", length = 50)
//    private LocalDateTime insertDateTime;            // 데이터 업데이트 일자
//
//    @Column(name="AT_ADDRESS" , length = 70)
//    private String address;
//
//    // custom constructor
//    public AptTradeEntity(AptTradeEntity aptTradeEntity){
//        this.id = aptTradeEntity.getId();
//        this.pnu = aptTradeEntity.getPnu();
//        this.name = aptTradeEntity.getName();
//        this.tradeDate = aptTradeEntity.getTradeDate();
//        this.tradeAmount = aptTradeEntity.getTradeAmount();
//        this.address = aptTradeEntity.getAddress();
//        this.insertDateTime = aptTradeEntity.getInsertDateTime();
//
//    }
//}
//
