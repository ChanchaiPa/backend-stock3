package com.demo.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) { 
		registry.addViewController("/").setViewName("forward:/index.html");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {   
		registry.addMapping("/**");
		//registry.addMapping("/**");
		//registry.addMapping("/product/*").allowedMethods("GET", "POST");
		//registry.addMapping("/product").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("https://www.w3schools.com", "https://www.w3schools_dev.com");
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
