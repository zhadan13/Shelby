package com.shelby.restaurant.shelby.gateway;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

@UtilityClass
public class GatewayRouteHelper {

    @Value("${shelbys.restaurant.service.path}")
    private String SERVICE_URL;

    public final String REGISTRATION_ROUTE = SERVICE_URL + "/registration";
    public final String LOGIN_ROUTE = SERVICE_URL + "/login";
}
