package com.xeo.ecommerce.backend.infrastructure.config;

import com.xeo.ecommerce.backend.infrastructure.jwt.JWTAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig {

    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfig(JWTAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(
                cors -> cors.configurationSource(
                        request -> {
                            CorsConfiguration configuration = new CorsConfiguration();
                            configuration.setAllowedOrigins(Arrays.asList("*"));
                            configuration.setAllowedMethods(Arrays.asList("*"));
                            configuration.setAllowedHeaders(Arrays.asList("*"));
                            return configuration;
                        })).csrf( csrf-> csrf.disable()).authorizeHttpRequests(
                aut -> aut.requestMatchers("/api/v1/admin/categories/**").permitAll()
                        .requestMatchers("/api/v1/admin/products/**").permitAll()
                        .requestMatchers("/api/v1/orders/**").permitAll()
                        .requestMatchers("/api/v1/payments/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/api/v1/home/**").permitAll()
                        .requestMatchers("/api/v1/security/**").permitAll().anyRequest().authenticated()
        ).addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) ;

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

}
