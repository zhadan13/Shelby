package com.shelby.restaurant.shelby.gateway.authorization;

import com.shelby.restaurant.shelby.dto.user.UserCreateRequest;
import reactor.core.publisher.Mono;

public interface RegistrationGateway {

    Mono<String> register(UserCreateRequest userCreateRequest);
}
