package com.shelby.restaurant.shelby.security;

import com.shelby.restaurant.shelby.dto.user.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@ConditionalOnProperty(name = "security.enabled", havingValue = "true", matchIfMissing = true)
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf()
                .disable()
                .cors()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint((serverWebExchange, e) -> Mono.fromRunnable(() -> serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                .accessDeniedHandler((serverWebExchange, e) -> Mono.fromRunnable(() -> serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                .authorizeExchange()
                .pathMatchers("/admin/**")
                .hasRole(UserRole.ADMIN.name())
                .pathMatchers("/**")
                .permitAll()
                .and()
                .build();
    }

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrationRepository(
            @Value("${spring.security.oauth2.client.registration.okta.client-id}") String clientId,
            @Value("${spring.security.oauth2.client.registration.okta.client-secret}") String clientSecret,
            @Value("${spring.security.oauth2.client.registration.okta.authorization-grant-type}") String authorizationGrantType,
            @Value("${spring.security.oauth2.client.registration.okta.scope}") String scope,
            @Value("${spring.security.oauth2.client.provider.okta.token-uri}") String tokenUri) {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("okta")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .scope(scope)
                .tokenUri(tokenUri)
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

    @Bean
    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrationRepository);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oAuth2AuthorizedClientExchangeFilterFunction = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oAuth2AuthorizedClientExchangeFilterFunction.setDefaultClientRegistrationId("okta");
        return WebClient.builder()
                .filter(oAuth2AuthorizedClientExchangeFilterFunction)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
