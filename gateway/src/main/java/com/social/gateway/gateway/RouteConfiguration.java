package com.social.gateway.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/users/**")
                        .uri("lb://user-service"))
                .route("post-service", r -> r.path("/api/posts/**")
                        .uri("lb://post-service"))
                .route("notification-service", r -> r.path("/api/notifications/**")
                        .uri("lb://notification-service"))
                .route("media-service", r -> r.path("/api/media/**")
                        .uri("lb://media-service"))
                .build();
    }
}