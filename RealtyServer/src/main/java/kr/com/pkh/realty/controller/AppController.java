package kr.com.pkh.realty.controller;

import java.util.List;

import kr.com.pkh.realty.dto.AppInfoDTO;
import kr.com.pkh.realty.dto.UserInfoDTO;
import kr.com.pkh.realty.entity.AppInfoEntity;
import kr.com.pkh.realty.repository.AppInfoRepository;
import kr.com.pkh.realty.service.UserInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.com.pkh.realty.service.AppInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 어플리케이션 정보 관리 컨트롤러
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/app")
public class AppController {


	private final UserInfoService userInfoservice;
	private final AppInfoService appInfoService;


	@GetMapping("list")
	public String getAppList(HttpServletRequest request, HttpServletResponse reponse, Model model,
						@RequestParam(value = "page", defaultValue = "1") int page) throws Exception {

		UserInfoDTO user = userInfoservice.getUser();
		Page<AppInfoDTO> paging = appInfoService.getAppList(page - 1);

		model.addAttribute("user", user);
		model.addAttribute("paging", paging);

		return "app/appList";
	}



}
