package kr.com.pkh.realty.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// JPA repository 객체에서 사용하는 객체 (=table 구조)
@Getter
@Setter
@Entity
@Table(name = "TB_USER_INFO")       // 테이블명
public class UserInfoEntity {


    // @GeneratedValue(strategy = GenerationType.IDENTITY)      = mariadb 의 auto increment 대체
    @Id
    @Column(name="UI_ID", length = 11)
    private int id;

    @Column(name ="UI_USER_ID" ,length = 20)
    private String userId;

    @Column(name ="UI_USER_PW" ,length = 100)
    private String userPw;

    @Column(name ="UI_USER_NAME" ,length = 50)
    private String userName;

}
