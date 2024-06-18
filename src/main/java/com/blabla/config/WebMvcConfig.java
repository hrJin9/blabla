package com.blabla.config;

import com.blabla.config.interceptor.AuthInterceptor;
import com.blabla.config.resolver.AuthArgumentResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    String[] ALLOWED_ORIGINS = {"http://13.209.16.211"};

    private static final String[] excludePaths = {"/swagger-ui/**","/api/docs/**", "/docs/**"};
    private static final String[] apiExcludePaths = {"/api/auth/login", "/api/auth/register", "/api/boards/**", "/api/categories/**", "/api/likes/**", "/api/tags/**"};

    private final AuthInterceptor authInterceptor;
    private final AuthArgumentResolver authArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(excludePaths)
                .excludePathPatterns(apiExcludePaths);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
}
