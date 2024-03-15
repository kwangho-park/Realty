package kr.com.pkh.realty.controller;

import kr.com.pkh.realty.dto.RegisterDTO;
import kr.com.pkh.realty.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor    // 의존 객체 주입 어노테이션 (=@Autowired 대체)
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserInfoService userInfoservice;

    @RequestMapping(method = RequestMethod.POST, value= "")
    public @ResponseBody String searchLogDTO(@RequestBody RegisterDTO registerDTO) throws Exception {

        log.info("사용자 신규 등록 (user id) : "+registerDTO.getUserId() );
        return String.valueOf(userInfoservice.registerUser(registerDTO));

    }

    // 기본 페이지 반환
}
