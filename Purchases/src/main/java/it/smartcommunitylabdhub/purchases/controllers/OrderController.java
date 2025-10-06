package it.smartcommunitylabdhub.purchases.controllers;


import it.smartcommunitylabdhub.purchases.feigns.OpenFeignCatalogService;
import it.smartcommunitylabdhub.purchases.models.Product;
import it.smartcommunitylabdhub.purchases.models.dtos.CardDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.OrderDTO;
import it.smartcommunitylabdhub.purchases.services.CartService;
import it.smartcommunitylabdhub.purchases.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    @Autowired
    OpenFeignCatalogService catalogService;

    private final OrderService orderService;
    @Autowired
    private CartService cartService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrders() {
       return ResponseEntity.status(200).body(orderService.getOrders());
    }

    @GetMapping("/{userId}/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable String userId, @PathVariable String orderId) {
        return ResponseEntity.status(200).body(orderService.getOrder(userId, orderId));
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<OrderDTO> checkout(
            @PathVariable String userId, @RequestBody CardDTO card) {
        OrderDTO order = orderService.checkout(userId, card);
        return ResponseEntity.status(200).body(order);
    }

}
