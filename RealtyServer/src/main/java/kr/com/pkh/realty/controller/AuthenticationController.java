package kr.com.pkh.realty.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.com.pkh.realty.dto.db.AptBuildingDTO;
import kr.com.pkh.realty.dto.db.UserInfoDTO;
import kr.com.pkh.realty.service.AptBuildingService;
import kr.com.pkh.realty.service.AuthenticationService;
import kr.com.pkh.realty.service.UserInfoService;
import kr.com.pkh.realty.util.SessionConst;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

/**
 * 사용자 로그인/로그아웃 컨트롤러 (Session)
 */
@Controller
@Log4j2
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	AptBuildingService aptBuildingService;

	@PostMapping("/login")
	public String login(@ModelAttribute UserInfoDTO form, BindingResult bindingResult,
						RedirectAttributes redirectAttributes, HttpServletResponse reponse, HttpServletRequest request, Model model)
			throws Exception {

		log.info("id: " + form.getUserId() + " / pw: " + form.getUserPw());
		UserInfoDTO userInfoDTO = userInfoService.getUserInfo(form);
		/*
		 * 네이버 지도 테스트
		 * todo: 소스코드 이동 필요
		 * */

		List<AptBuildingDTO> aptGpsList = aptBuildingService.getAptBuildingGpsList();
		model.addAttribute("aptGpsList",aptGpsList);
		ObjectMapper objectMapper = new ObjectMapper();
		String aptGpsJson = objectMapper.writeValueAsString(aptGpsList);
		model.addAttribute("aptGpsJson",aptGpsJson);
		if (userInfoDTO == null) {
			model.addAttribute("msg", "로그인 실패");
			return "login/index";
		}

		// 로그인 성공
		HttpSession session = request.getSession();

		// 세션에 로그인 회원정보 보관
		session.setAttribute(SessionConst.LOGIN_USER, userInfoDTO);
		log.info("login Info {} ", (UserInfoDTO) session.getAttribute(SessionConst.LOGIN_USER));
		redirectAttributes.addFlashAttribute("msg", "로그인 성공");


		return "redirect:/";
	}
	
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {

		HttpSession session = request.getSession(false); // false 인 경우 세션이 있는경우 기존세션 반환, 세션 없는 경우 null 반환

		if (session != null) {
			// 세션 삭제
			session.invalidate();
			
			// 추후 : magnager 의 db를 받아오기 떄문에 로그아웃시 user데이터 삭제	
		}

		return "redirect:/";

	}
}
