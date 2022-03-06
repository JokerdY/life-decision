package com.life.decision.support.config;

import com.life.decision.support.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        RequestInterceptor interceptor = new RequestInterceptor();
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }
}
