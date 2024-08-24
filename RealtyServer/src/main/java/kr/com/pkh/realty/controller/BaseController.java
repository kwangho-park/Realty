package kr.com.pkh.realty.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.com.pkh.realty.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


@Slf4j
public class BaseController {
    public boolean hassAccess(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // 세션 체크
        if(session == null
                || (UserInfoDTO) session.getAttribute("userInfo") == null
                || StringUtils.isEmpty(((UserInfoDTO) session.getAttribute("userInfo")).getUserId())) {
            log.info("SESSION INVALIDATED ");
            return false;
        }
        // 세션 권한 체크
        log.info("[SESSION ID : {} , userId : {} ]", session.getId());
        return true;
    }
}
