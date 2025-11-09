package it.smartcommunitylabdhub.purchases.controllers;

import it.smartcommunitylabdhub.purchases.feigns.OpenFeignCatalogService;
import it.smartcommunitylabdhub.purchases.models.dtos.CardDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.OrderDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.PaymentDTO;
import it.smartcommunitylabdhub.purchases.services.CartService;
import it.smartcommunitylabdhub.purchases.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OpenFeignCatalogService catalogService;

    private final OrderService orderService;
    
    @Autowired
    private CartService cartService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable String userId) {
        try {
            List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        try {
            Optional<OrderDTO> order = orderService.getOrderById(orderId);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<OrderDTO> checkout(
            @PathVariable String userId, 
            @RequestBody CardDTO card) {
        try {
            // Create payment DTO from card
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setUserId(userId);
            paymentDTO.setCurrency("EUR");
            paymentDTO.setPaymentMethod("CARD");
            
            // Get cart to determine amount
            Optional<it.smartcommunitylabdhub.purchases.models.dtos.CartDTO> cart = cartService.getCartByUserId(userId);
            if (cart.isEmpty() || cart.get().getTotalPrice() == null) {
                return ResponseEntity.badRequest().build();
            }
            
            paymentDTO.setAmount(cart.get().getTotalPrice());
            
            OrderDTO order = orderService.createOrder(userId, paymentDTO);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable String status) {
        try {
            List<OrderDTO> orders = orderService.getOrdersByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId, 
            @RequestParam String status) {
        try {
            OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        try {
            List<OrderDTO> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
