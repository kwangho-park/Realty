package kr.com.pkh.realty.controller;

import kr.com.pkh.realty.repository.UserInfoRepository;
import kr.com.pkh.realty.util.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.com.pkh.realty.entity.UserInfoEntity;
import kr.com.pkh.realty.service.UserInfoService;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 정보관리 컨트롤러
 */
@RequiredArgsConstructor    // 의존 객체 주입 어노테이션 (=@Autowired 대체)
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserInfoRepository userRepository;
    private final UserInfoService userInfoservice;

    @GetMapping("/info")
    public String userInfo(HttpServletRequest request, HttpServletResponse reponse, Model model) {
    	
    	UserInfoEntity userInfo = (UserInfoEntity) request.getSession().getAttribute(SessionConst.LOGIN_USER);
    	
    	model.addAttribute("user", userInfo);
    	
    	return "/user/user";
    }
    
//    @GetMapping("/info")
//    public String userInfo(HttpServletRequest request, HttpServletResponse reponse, Model model) {
//    	
////    	UserInfoEntity userInfo = (UserInfoEntity) request.getSession().getAttribute(SessionConst.LOGIN_USER);
//    	
//    	UserInfoDTO user = userInfoservice.getUser();
//    	
//    	
//    	model.addAttribute("user", user);
//    	
//    	return "/user/user";
//    }
    
    
    
    
    
//    @GetMapping("/list")
//    public String userlist(Model model) {
//        List<UserInfoEntity> userList = this.userRepository.findAll();
//        model.addAttribute("userList", userList);
//        return "user/user";      // template 파일명 (=html 파일)
//    }
}
