package com.nftapp.nftapp.Auth;

import com.nftapp.nftapp.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor

public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager auth(UserService users) {
        return new ProviderManager(new Web3AuthenticationProvider(users));
    }

    @Bean
    public WebMvcConfigurer cors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOriginPatterns("http://localhost:8019")
                        .allowedMethods("*");
            }
        };
    }
    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(HttpMethod.OPTIONS, "/auth").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/collection").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/user").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/items").permitAll()
                                .requestMatchers("/api/auth/*").permitAll()
                                .anyRequest().permitAll()
                );

        return http.build();
    }
}
