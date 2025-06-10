package com.sis.fems.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CrosConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 요청 경로에 대해
                .allowedOrigins("http://localhost:5173") // React dev 서버 주소
                .allowedMethods("*") // GET, POST 등 모든 메서드 허용
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}