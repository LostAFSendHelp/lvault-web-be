package com.example.lostaf.lvaultweb.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class OAuth2LoginSecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        

        return http
            .csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(requests ->
                requests
                    .requestMatchers("/", "/error", "/login/oauth2/google/code").permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(configurer ->
                configurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .build();
    }
}
