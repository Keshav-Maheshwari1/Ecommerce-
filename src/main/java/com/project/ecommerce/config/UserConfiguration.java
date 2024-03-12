package com.project.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class UserConfiguration implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer cors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/users/create")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                        .allowCredentials(true);
                registry.addMapping("/api/users/signIn")
                        .allowedOrigins("http://localhost:3000")
                        .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                        .allowedMethods("GET","POST")
                        .allowCredentials(true);
                registry.addMapping("/api/users/reset")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                        .allowCredentials(true);
                registry.addMapping("/api/users/logout/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST","DELETE")
                        .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                        .allowCredentials(true);
                registry.addMapping("/api/users/payment/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                        .allowCredentials(true);
                registry.addMapping("/api/users/ordre")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
                        .allowCredentials(true);

            }
        };
    }
}
