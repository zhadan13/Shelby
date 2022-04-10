package com.shelby.restaurant.shelby.gateway.authorization.impl;

import com.shelby.restaurant.shelby.dto.authorization.LoginRequest;
import com.shelby.restaurant.shelby.dto.user.User;
import com.shelby.restaurant.shelby.gateway.GatewayRouteHelper;
import com.shelby.restaurant.shelby.gateway.authorization.LoginGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginGatewayImpl implements LoginGateway {

    private final WebClient webClient;

    @Override
    public Mono<User> login(LoginRequest loginRequest) {
        log.info("Making call to service with login request");
        return webClient.post()
                .uri(GatewayRouteHelper.LOGIN_ROUTE)
                .body(BodyInserters.fromValue(loginRequest))
                .retrieve()
                .bodyToMono(User.class);
    }
}
