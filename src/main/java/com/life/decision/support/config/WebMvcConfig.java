package com.life.decision.support.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        RequestInterceptor interceptor = new RequestInterceptor();
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(    //添加不拦截路径
                        "/**/login",
                        "/**/register",
                        "/sysDict/**",
                        "/**/*.html",
                        "/**/*.js",
                        "/**/*.css"
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
