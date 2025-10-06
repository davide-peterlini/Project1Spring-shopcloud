package it.smartcommunitylabdhub.shopgateway.configs;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("catalog_root", r -> r
                        .path("/catalog")

                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("catalog")
                                        .setFallbackUri("forward:/fallback/catalog"))
                                .setPath("/api/products"))
                        .uri("lb://catalog"))

                .route("catalog_all", r -> r
                        .path("/catalog/**")
                        .filters(f ->
                                f.circuitBreaker(config -> config
                                                .setName("catalog")
                                                .setFallbackUri("forward:/fallback/catalog"))
                                        .stripPrefix(1).prefixPath("/api/products"))
                        .uri("lb://catalog"))
                //purchase
                .route("purchase_root", r -> r
                        .path("/purchase")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("purchase")
                                        .setFallbackUri("forward:/fallback/purchase"))
                                .setPath("/api/orders"))
                        .uri("lb://purchase"))

                .route("purchase_all", r -> r
                        .path("/purchase/**")
                        .filters(f ->
                                f.circuitBreaker(config -> config
                                                .setName("purchase")
                                                .setFallbackUri("forward:/fallback/purchase"))
                                        .stripPrefix(1).prefixPath("/api/orders"))
                        .uri("lb://purchase"))
                .route("cart_root", r -> r
                        .path("/cart")
                        .filters(f -> f.setPath("/api/carts"))
                        .uri("lb://purchase"))
                .route("cart_all", r -> r
                        .path("/cart/**")
                        .filters(f ->
                                f.stripPrefix(1).prefixPath("/api/carts"))
                        .uri("lb://purchase"))
                .build();

    }
}
