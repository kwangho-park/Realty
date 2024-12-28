package kr.com.pkh.realty.interceptor;

import kr.com.pkh.realty.util.SessionConst;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Log4j2
public class LoginCheckInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestURI = request.getRequestURI();
		log.info("[interceptor] : " + requestURI);
		HttpSession session = (HttpSession) request.getSession(false);

		
		if(session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
			// 미 로그인
			log.info("[미인증 사용자 요청]");
			
			// 로그인 화면 redirect
			response.sendRedirect("/login");
			return false;
		}
		
		return true;
	}

	// 요청이 처리된 후, 뷰가 렌더링되기 전에 호출
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
		System.out.println("After handler execution, but before view rendering");

		// 모델에 데이터 추가 가능
		if (modelAndView != null) {
			modelAndView.addObject("interceptorMessage", "This is added by MyHandlerInterceptor");
		}
	}

	// 요청 처리가 끝난 후 호출됩니다. 뷰 렌더링 후 호출
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
								Exception ex) throws Exception {
		System.out.println("After request completion");
	}
}
