package com.example.lostaf.lvaultweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.lostaf.lvaultweb.repositories.custom.CustomRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
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
					.addMapping("/login/**")
					.allowedOrigins("http://localhost:5173");
			}
		};
	}
}
