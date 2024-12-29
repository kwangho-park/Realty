package kr.com.pkh.realty.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.com.pkh.realty.dto.db.AptBuildingDTO;
import kr.com.pkh.realty.dto.db.UserInfoDTO;
import kr.com.pkh.realty.service.AptBuildingService;
import kr.com.pkh.realty.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
@RequiredArgsConstructor    // 의존 객체 주입 어노테이션 (=@Autowired 대체)
@Controller
@RequestMapping("/realtyMap")
@Slf4j
public class RealtyMapController {

    @Autowired
    AptBuildingService aptBuildingService;

    @GetMapping("/info")
    public String realtyMap(HttpServletRequest request, Model model)throws Exception  {


        // 세션반환
        HttpSession session = request.getSession(false);

        // [고도화] filter 또는 spring interceptor 에서  session check 하도록 고도화 필요함
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

            List<AptBuildingDTO> aptGpsList = aptBuildingService.getAptBuildingGpsList();
            model.addAttribute("aptGpsList",aptGpsList);
            ObjectMapper objectMapper = new ObjectMapper();
            String aptGpsJson = objectMapper.writeValueAsString(aptGpsList);
            model.addAttribute("aptGpsJson",aptGpsJson);

            return "realtyMap/realtyMap";
        }

    }
}
