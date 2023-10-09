package kr.com.pkh.realty.interceptor;

import kr.com.pkh.realty.util.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoginCheckInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestURI = request.getRequestURI();
		log.info("[interceptor] : " + requestURI);
		HttpSession session = request.getSession(false);

		
		if(session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
			// 미 로그인
			log.info("[미인증 사용자 요청]");
			
			// 로그인 화면 redirect
			response.sendRedirect("/login");
			return false;
		}
		
		return true;
	}
}
