package kr.com.pkh.realty.controller;

import kr.com.pkh.realty.dto.db.UserInfoDTO;
import kr.com.pkh.realty.dto.request.RegisterRequestDTO;
import kr.com.pkh.realty.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor    // 의존 객체 주입 어노테이션 (=@Autowired 대체)
@Controller
@RequestMapping("/register")
public class RegisterController {


    @Autowired
    UserInfoService userInfoService;


    // legacy
//    @RequestMapping(method = RequestMethod.POST, value= "")
//    public @ResponseBody String searchLogDTO(@RequestBody @Valid UserInfoDTO userInfoDTO) throws Exception {
//
//        log.info("사용자 신규 등록 (user id) : "+userInfoDTO.getUserId() );
//        return String.valueOf(userInfoservice.registUser(userInfoDTO));
//
//    }


    @PostMapping("")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequestDTO registerRequest, RedirectAttributes redirectAttributes) {

        log.info("user id : {} ",registerRequest.getUserId());
        log.info("user pw : {} ",registerRequest.getUserPw());
        log.info("user name : {} ",registerRequest.getUserName());

        String resultCode="";
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        Map<String, String> response = new HashMap<>();

        userInfoDTO.setUserId(registerRequest.getUserId());
        userInfoDTO.setUserPw(registerRequest.getUserPw());
        userInfoDTO.setUserName(registerRequest.getUserName());

        try {
            // 회원가입 처리
            resultCode = userInfoService.registUser(userInfoDTO);

            if ("1".equals(resultCode)) {
                response.put("status", "success");
                response.put("message", "회원가입이 성공적으로 완료되었습니다. \n 당신의 개인정보는 이제 제껍니다^0^");
            } else if ("2".equals(resultCode)) {
                response.put("status", "error");
                response.put("message", "회원가입 중 오류가 발생했습니다.");
            } else if ("3".equals(resultCode)) {
                response.put("status", "duplicate");
                response.put("message", "아이디가 이미 존재합니다.");
            } else {
                response.put("status", "unknown");
                response.put("message", "알 수 없는 오류가 발생했습니다.");
            }


        } catch (Exception e) {
            log.error("회원가입 처리 중 예외 발생", e);
            response.put("status", "error");
            response.put("message", "서버 내부 오류가 발생했습니다.");
        }

        // HTTP body 데이터를 JSON String 으로 응답
        return ResponseEntity.ok(response);
    }

}
