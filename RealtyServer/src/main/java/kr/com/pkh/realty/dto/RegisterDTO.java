package kr.com.pkh.realty.dto;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class RegisterDTO {

    @NotNull
    @NotEmpty
    @Length(max=20, message="userId 의 길이는 20를 초과 할 수 없습니다.")
    @Pattern(regexp="[a-zA-Z0-9]{0,20}" , message = "systemId 는 20자 이하의 영문 또는 숫자입니다.")
    String userId;

    @NotNull
    @NotEmpty
    @Length(max=20, message="userPw 의 길이는 20를 초과 할 수 없습니다.")
    @Pattern(regexp="[a-zA-Z0-9]{0,20}" , message = "systemId 는 20자 이하의 영문 또는 숫자입니다.")
    String userPw;

    @NotEmpty
    @NotNull
    @Length(max=50, message="userName 의 길이는 50를 초과 할 수 없습니다.")
    String userName;
}
