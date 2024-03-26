package kr.com.pkh.realty.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// JPA repository 객체에서 사용하는 객체 (=table 구조)
@Data
@Entity
@Table(name = "TB_USER_INFO")       // 테이블명
public class UserInfoEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)      // auto increment 또는 시퀀스 정의 (db의 key값을 사용)
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
