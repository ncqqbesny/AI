package com.app.device.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

//	/**
//	 * 前后端分离后引起的跨域访问
//	 * 
//	 * @return
//	 */
//	@Bean
//	public CorsFilter corsFilter() {
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		final CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.setAllowedHeaders(Arrays.asList("*"));
//		config.setAllowedOrigins(Arrays.asList("*"));
//		config.setAllowedMethods(Arrays.asList("*"));
//		config.setMaxAge(10000L);
//		source.registerCorsConfiguration("/**", config);
//		return new CorsFilter(source);
//	}

	/**
	 * 开启跨域
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 设置允许跨域的路由
		registry.addMapping("/**").allowedMethods("GET", "HEAD", "POST", "DELETE", "OPTIONS")
				.allowCredentials(true).maxAge(3600).allowedHeaders("*");
	}

}
