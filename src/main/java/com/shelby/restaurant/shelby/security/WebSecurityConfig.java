package com.shelby.restaurant.shelby.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers("/**")
                .permitAll()
                .and()
                .build();
    }
}
