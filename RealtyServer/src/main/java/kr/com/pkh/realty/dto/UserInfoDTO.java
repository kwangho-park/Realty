package kr.com.pkh.realty.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data   // @Getter, @Setter, @RequiredArgsConstructor 등이 포함된 어노테이션
@Builder
@ToString
public class UserInfoDTO {
	
    private int id;
    private String userId;
    private String userPw;
    private String userName;
    
}
