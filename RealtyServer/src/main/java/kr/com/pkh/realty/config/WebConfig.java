package kr.com.pkh.realty.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// spring boot 의 서버 설정
// =spring 의 web.xml 설정 대체
@Component
public class WebConfig implements WebMvcConfigurer{
	

    private static final String[] EXCLUDEPATHPATTERNS = {
            "/*.ico", "/*.css", "/*.js", "/*.map",
            "/bootstrap/**", "/datatables/**", "/datatables-plugins/**", "/dreamsecurity/**",
            "/font-awsome/**", "/jquery/**", "/metisMenu/**", "/usedTemplate/**", "/favicon.ico",
    };

	// 정적 자원 매핑 핸들러 ([homepath]/src/main/resource/ 하위에 위치한 자원 )
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// /img/** 경로로 요청 시, resources/img 폴더의 파일을 제공
		registry.addResourceHandler("/img/**")
				.addResourceLocations("classpath:/img/");
	}
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

/*
		registry.addInterceptor(new LoginCheckInterceptor()) // LoginCheckInterceptor 등록
//		.order(1)	// 적용할 필터 순서 설정
		.addPathPatterns("", "/**")
		.excludePathPatterns(EXCLUDEPATHPATTERNS)	// static 디렉토리의 하위 파일 목록은 인증 무시 (=항상 통과)
		.excludePathPatterns("/error", "/session/login", "/login" , "/register"); // 인터셉터에서 제외할 패턴*/
	}



}
