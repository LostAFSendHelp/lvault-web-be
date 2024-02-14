package com.example.lostaf.lvaultweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LvaultWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LvaultWebApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping("/vaults/**")
					.allowedOrigins("http://localhost:5173");

				registry
					.addMapping("/vault/**")
					.allowedOrigins("http://localhost:5173");
			}
		};
	}
}
