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
		}


		// 세션이 형성된 사용자인 경우 (로그인 상태) //
		List<String> typeSSH = new ArrayList<>();
		List<String> typeRDP = new ArrayList<>();
		
		for(AppInfoEntity app : appList) {
			if(app.getAppProtocol().equals("SSH")) {
				typeSSH.add(app.getAppProtocol());
			} else if(app.getAppProtocol().equals("RDP")){
				typeRDP.add(app.getAppProtocol());
			}
		}

		model.addAttribute("user", user);
		model.addAttribute("app", appList);
		model.addAttribute("typeSSH", typeSSH);
		model.addAttribute("typeRDP", typeRDP);
		
		
		return "user/user";		// web view path
	}
    
    @GetMapping("/login")
    public String login() {
        return "login/index";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/dashboard";
    }
    
//    @GetMapping("/app")
//    public String application() {
//        return "application/applicationList";
//    }
    
//    @GetMapping("/user")
//    public String user() {
//    	return "user/user";
//    }

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
    
//    @GetMapping("/")
//    public String index() {
//        return "redirect:/login";
//    }

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

}
