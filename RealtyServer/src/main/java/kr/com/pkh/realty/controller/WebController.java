package kr.com.pkh.realty.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.com.pkh.realty.dto.AptBuildingDTO;
import kr.com.pkh.realty.dto.UserInfoDTO;
import kr.com.pkh.realty.service.AptBuildingService;
import kr.com.pkh.realty.service.UserInfoService;
import kr.com.pkh.realty.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


/**
 * index 페이지 및 메뉴분기 처리를 위한 컨트롤러
 * domain home : [ip]:[port]/login
 */
@RequiredArgsConstructor
@Controller
@Log4j2
public class WebController extends BaseController {
	;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	AptBuildingService aptBuildingService;

	@GetMapping("/")
	public String homeLogin(HttpServletRequest request, Model model) {

		// 세션반환
		HttpSession session = request.getSession(false);

		// 세션이 형성되지않은 사용자인 경우 //
		if(session == null) {
			return "redirect:/login";
		}

		UserInfoDTO user = (UserInfoDTO)session.getAttribute(SessionConst.LOGIN_USER);

		if(user == null) {
			model.addAttribute("msg", "로그인 실패");
			return "login";

		}else{	// 로그인 되어 있는 사용자인 경우
			model.addAttribute("user", user);
			model.addAttribute("app", 0);

			return "/user/user";		// web view path
		}


	}
    
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) throws JsonProcessingException {
		/*
		 * 네이버 지도 테스트
		 * todo: 소스코드 이동 필요
		 * */


		List<AptBuildingDTO> aptGpsList = aptBuildingService.getAptBuildingGpsList();
		model.addAttribute("aptGpsList",aptGpsList);
		ObjectMapper objectMapper = new ObjectMapper();
		String aptGpsJson = objectMapper.writeValueAsString(aptGpsList);
		model.addAttribute("aptGpsJson",aptGpsJson);


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
