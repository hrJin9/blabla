package com.blabla.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    private static final String[] excludePaths = {"/api/login/**","/api/swagger-ui/**","/api/docs/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor()
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login/**")
                .excludePathPatterns(excludePaths);
    }

}
