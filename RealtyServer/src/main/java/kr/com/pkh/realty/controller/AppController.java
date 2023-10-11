package kr.com.pkh.realty.controller;

import java.util.List;

import kr.com.pkh.realty.dto.AppInfoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.com.pkh.realty.service.AppInfoService;
import lombok.RequiredArgsConstructor;

/**
 * 어플리케이션 정보 관리 컨트롤러
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/app")
public class AppController {

	private final AppInfoService appInfoService;

	@GetMapping("list")
	public String index(HttpServletRequest request, HttpServletResponse reponse, Model model) throws Exception {
		
		List<AppInfoDTO> listApp = appInfoService.findAll();
		
		model.addAttribute("app", listApp);
		
		return "app/appList";
	}
	
}
