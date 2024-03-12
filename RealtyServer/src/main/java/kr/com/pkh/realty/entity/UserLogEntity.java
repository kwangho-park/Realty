package kr.com.pkh.realty.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_USER_LOG")       // 테이블명
public class UserLogEntity {
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
