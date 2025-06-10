package com.sis.fems.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


// 내부 테스트 Cros 설정
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeHttpRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
