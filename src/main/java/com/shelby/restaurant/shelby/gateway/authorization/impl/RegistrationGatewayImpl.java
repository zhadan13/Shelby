package com.shelby.restaurant.shelby.gateway.authorization.impl;

import com.shelby.restaurant.shelby.dto.user.UserCreateRequest;
import com.shelby.restaurant.shelby.gateway.GatewayRouteHelper;
import com.shelby.restaurant.shelby.gateway.authorization.RegistrationGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationGatewayImpl implements RegistrationGateway {

    private final WebClient webClient;

    @Override
    public Mono<String> register(UserCreateRequest userCreateRequest) {
        log.info("Making call to service with registration request");
        return webClient.post()
                .uri(GatewayRouteHelper.REGISTRATION_ROUTE)
                .body(BodyInserters.fromValue(userCreateRequest))
                .retrieve()
                .bodyToMono(String.class);
    }
}
