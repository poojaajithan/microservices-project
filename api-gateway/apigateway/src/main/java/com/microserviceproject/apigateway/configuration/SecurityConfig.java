package com.microserviceproject.apigateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(
                    "/actuator/**",
                    "/",
                    "/login/**",
                    "/oauth2/**",
                    "/eureka/**"
                ).permitAll()
                .anyExchange().authenticated()
            )
            .oauth2Login() // separate configuration for login
            .and()         // return to the ServerHttpSecurity builder
            .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt); // configure JWT

        return http.build();
    }
}