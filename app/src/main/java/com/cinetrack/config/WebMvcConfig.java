package com.cinetrack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ProfileInterceptor profileInterceptor;

    @Autowired
    public WebMvcConfig(ProfileInterceptor profileInterceptor) {
        this.profileInterceptor = profileInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(profileInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login", "/logout",
                        "/registro", "/registro/**",
                        "/perfiles", "/perfiles/**",
                        "/css/**", "/js/**", "/img/**",
                        "/favicon.ico", "/error",
                        "/health"
                );
    }
}
