package it.outdoor.payment.feigns;


import it.outdoor.payment.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignPurchaseServiceFallback {

    Order getOrder(String id) {
        return Order.builder().build();
    }

    boolean verifyProductAvailability(String id, Integer quantity) {
        return false;
    }

    void changeProductAvailability(String id, Integer quantity) {
    }
}
