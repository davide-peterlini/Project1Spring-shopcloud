package it.outdoor.payment.feigns;

import it.outdoor.payment.models.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OpenFeignPurchaseServiceFallback implements OpenFeignPurchaseService {

    @Override
    public Optional<Order> getOrder(String userId, String orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(userId);
        order.setStatus("UNAVAILABLE");
        return Optional.of(order);
    }

    @Override
    public boolean verifyProductAvailability(String id, Integer quantity) {
        return false; // Fallback assumes product is not available
    }

    @Override
    public void changeProductAvailability(String id, Integer quantity) {
        // Fallback implementation - do nothing
    }
}
