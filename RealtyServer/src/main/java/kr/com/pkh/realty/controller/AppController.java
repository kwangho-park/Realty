package kr.com.pkh.realty.controller;

import kr.com.pkh.realty.dto.db.UserInfoDTO;
import kr.com.pkh.realty.service.AppInfoService;
import kr.com.pkh.realty.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 어플리케이션 정보 관리 컨트롤러
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/app")
public class AppController {


	private final UserInfoService userInfoservice;
	private final AppInfoService appInfoService;


	// legacy (타임리프 리스트 출력 참고용 )
	@GetMapping("list")
	public String getAppList(HttpServletRequest request, HttpServletResponse reponse, Model model,
						@RequestParam(value = "page", defaultValue = "1") int page) throws Exception {

		UserInfoDTO user = userInfoservice.getUser();
		//Page<AppInfoDTO> paging = appInfoService.getAppList(page - 1);

		model.addAttribute("user", user);
		model.addAttribute("paging",0);

		return "app/appList";
	}



}
