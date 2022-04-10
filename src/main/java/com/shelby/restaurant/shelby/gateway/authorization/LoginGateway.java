package com.shelby.restaurant.shelby.gateway.authorization;

import com.shelby.restaurant.shelby.dto.authorization.LoginRequest;
import com.shelby.restaurant.shelby.dto.user.User;
import reactor.core.publisher.Mono;

public interface LoginGateway {

    Mono<User> login(LoginRequest loginRequest);
}
