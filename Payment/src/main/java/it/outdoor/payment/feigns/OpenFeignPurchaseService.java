package it.outdoor.payment.feigns;

import it.outdoor.payment.models.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(value = "purchase", fallback = OpenFeignPurchaseServiceFallback.class)
public interface OpenFeignPurchaseService {

    @GetMapping("/api/orders/{userId}/{orderId}")
    Optional<Order> getOrder(@PathVariable String userId, @PathVariable String orderId);

}