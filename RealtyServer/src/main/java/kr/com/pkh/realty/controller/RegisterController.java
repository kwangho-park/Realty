package kr.com.pkh.realty.controller;

import kr.com.pkh.realty.dto.UserInfoDTO;
import kr.com.pkh.realty.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;



@Slf4j
@RequiredArgsConstructor    // 의존 객체 주입 어노테이션 (=@Autowired 대체)
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserInfoService userInfoservice;

    @RequestMapping(method = RequestMethod.POST, value= "")
    public @ResponseBody String searchLogDTO(@RequestBody @Valid UserInfoDTO userInfoDTO) throws Exception {

        log.info("사용자 신규 등록 (user id) : "+userInfoDTO.getUserId() );
        return String.valueOf(userInfoservice.registUser(userInfoDTO));

    }

    // 기본 페이지 반환
}
