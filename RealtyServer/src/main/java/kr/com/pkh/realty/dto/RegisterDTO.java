package kr.com.pkh.realty.dto;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class RegisterDTO {
    String userId;
    String userPw;
    String userName;
}
