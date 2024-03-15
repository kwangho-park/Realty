package kr.com.pkh.realty.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.com.pkh.realty.repository.AppInfoRepository;
import kr.com.pkh.realty.util.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.com.pkh.realty.entity.AppInfoEntity;
import kr.com.pkh.realty.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * index 페이지 및 메뉴분기 처리를 위한 컨트롤러
 */
@RequiredArgsConstructor
@Controller
@Log4j2
public class WebController {
	
	private final AppInfoRepository appInfoRepository;

	@GetMapping("/")
	public String homeLogin(HttpServletRequest request, Model model) {
		
		// 세션반환
		HttpSession session = request.getSession(false);
		
		// 세션이 형성되지않은 사용자인 경우 //
		if(session == null) {
			return "redirect:/login";
		}
		
		UserInfoEntity user = (UserInfoEntity)session.getAttribute(SessionConst.LOGIN_USER);
		List<AppInfoEntity> appList = appInfoRepository.findAll();
		
		if(user == null) {
			model.addAttribute("msg", "로그인 실패");
			return "login";

		}else{	// 로그인 되어 있는 사용자인 경우
			model.addAttribute("user", user);
			model.addAttribute("app", appList);

			return "user/user";		// web view path
		}


	}
    
    @GetMapping("/login")
    public String login() {
        return "login/index";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/dashboard";
    }


    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	if(session == null) {
    		return "redirect:/";
    	}
    	
    	log.info("sessionId = {}", session.getId());
    	log.info("getMaxInacativeInterval={}", session.getMaxInactiveInterval());
    	log.info("creationTime={}", new Date(session.getCreationTime()));
    	log.info("lastTime={}", new Date(session.getLastAccessedTime()));
    	
    	return "redirect:/";
    
    }

    @GetMapping("/register")
    public String register() {
        return "register/register";
    }

}
