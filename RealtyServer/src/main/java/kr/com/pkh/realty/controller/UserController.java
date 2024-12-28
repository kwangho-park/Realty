package kr.com.pkh.realty.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.com.pkh.realty.dto.AptBuildingDTO;
import kr.com.pkh.realty.dto.UserInfoDTO;
import kr.com.pkh.realty.service.AptBuildingService;
import kr.com.pkh.realty.service.UserInfoService;
import kr.com.pkh.realty.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 사용자 정보관리 컨트롤러
 */
@RequiredArgsConstructor    // 의존 객체 주입 어노테이션 (=@Autowired 대체)
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserInfoService userInfoservice;
    @Autowired
    AptBuildingService aptBuildingService;

    @GetMapping("/info")
    public String userInfo(HttpServletRequest request, HttpServletResponse reponse, Model model) throws  Exception {

    	UserInfoDTO userInfo = (UserInfoDTO) request.getSession().getAttribute(SessionConst.LOGIN_USER);


        List<AptBuildingDTO> aptGpsList = aptBuildingService.getAptBuildingGpsList();
        model.addAttribute("aptGpsList",aptGpsList);
        ObjectMapper objectMapper = new ObjectMapper();
        String aptGpsJson = objectMapper.writeValueAsString(aptGpsList);
        model.addAttribute("aptGpsJson",aptGpsJson);

        model.addAttribute("user", userInfo);
    	
    	return "user/user";
    }



}
