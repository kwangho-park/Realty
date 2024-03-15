package kr.com.pkh.realty.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.com.pkh.realty.interceptor.LogInterceptor;
import kr.com.pkh.realty.interceptor.LoginCheckInterceptor;

// spring boot 의 서버 설정
// =spring 의 web.xml 설정 대체
@Component
public class WebConfig implements WebMvcConfigurer{
	

    private static final String[] EXCLUDEPATHPATTERNS = {
            "/*.ico", "/*.css", "/*.js", "/*.map",
            "/bootstrap/**", "/datatables/**", "/datatables-plugins/**", "/dreamsecurity/**",
            "/font-awsome/**", "/jquery/**", "/metisMenu/**", "/usedTemplate/**", "/favicon.ico",
    };
    
    
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 추후 사용 interceptor
		/*
		registry.addInterceptor(new LogInterceptor())
//		.order(1)	// 적용할 필터 순서 설정
//		.addPathPatterns("", "/**")
		.excludePathPatterns(EXCLUDEPATHPATTERNS)
		.excludePathPatterns("/error", "/session/login", "/login" ,"/register");
		*/


		registry.addInterceptor(new LoginCheckInterceptor()) // LoginCheckInterceptor 등록
//		.order(1)	// 적용할 필터 순서 설정
		.addPathPatterns("", "/**")
		.excludePathPatterns(EXCLUDEPATHPATTERNS)	// static 디렉토리의 하위 파일 목록은 인증 무시 (=항상 통과)
		.excludePathPatterns("/error", "/session/login", "/login" , "/register"); // 인터셉터에서 제외할 패턴
	}

}
