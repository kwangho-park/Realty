package kr.com.pkh.realty.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "TB_USER_LOG")       // 테이블명
public class UserLogEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="UL_ID", length = 11)      // ai
    private int id;

    @Column(name ="UL_USER_ID" ,length = 20)
    private String userId;

    @Column(name ="UL_USER_NAME" ,length = 50)
    private String userName;

    @Column(name ="UL_ACTION" ,length = 10)
    private String action;

    @Column(name ="UL_DATETIME")
    private String datetime;
}
