package kr.com.pkh.realty.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.com.pkh.realty.interceptor.LogInterceptor;
import kr.com.pkh.realty.interceptor.LoginCheckInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer{
	

    private static final String[] EXCLUDEPATHPATTERNS = {
            "/*.ico", "/*.css", "/*.js", "/*.map",
            "/bootstrap/**", "/datatables/**", "/datatables-plugins/**", "/dreamsecurity/**",
            "/font-awsome/**", "/jquery/**", "/metisMenu/**", "/usedTemplate/**", "/favicon.ico",
    };
    
    
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new LogInterceptor()) // LogInterceptor 등록
//		.order(1)	// 적용할 필터 순서 설정
//		.addPathPatterns("", "/**")
//		 static 디렉토리의 하위 파일 목록은 인증 무시 (=항상 통과)
		.excludePathPatterns(EXCLUDEPATHPATTERNS)
		.excludePathPatterns("/error", "/session/login", "/login"); // 인터셉터에서 제외할 패턴

		
		registry.addInterceptor(new LoginCheckInterceptor()) // LogInterceptor 등록
//		.order(1)	// 적용할 필터 순서 설정
//		.addPathPatterns("", "/**") 
		// static 디렉토리의 하위 파일 목록은 인증 무시 (=항상 통과)
		.excludePathPatterns(EXCLUDEPATHPATTERNS)
		.excludePathPatterns("/error", "/session/login", "/login"); // 인터셉터에서 제외할 패턴
}

}
