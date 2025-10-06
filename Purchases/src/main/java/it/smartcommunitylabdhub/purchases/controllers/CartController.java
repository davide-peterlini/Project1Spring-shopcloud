package it.smartcommunitylabdhub.purchases.controllers;

import it.smartcommunitylabdhub.purchases.models.Cart;
import it.smartcommunitylabdhub.purchases.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<Cart>> getCart(@PathVariable String userId) {
        return ResponseEntity.status(200).body(
                cartService.getCart(userId)
        );
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Optional<Cart>> updateCart(@PathVariable String userId, Cart cart) {
        try {
            return ResponseEntity.status(200).body(
                    cartService.updateCart(userId, cart)
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteCart(@PathVariable String userId) {
        try {
            cartService.deleteCart(userId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
        return ResponseEntity.status(200).body(true);
    }

    @PutMapping("/{userId}/{productId}/{quantity}")
    public ResponseEntity<Optional<Cart>> addProductToCart(
            @PathVariable String userId,
            @PathVariable String productId,
            @PathVariable Integer quantity) {
        try {
            cartService.addProductToCart(userId, productId, quantity);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
        return ResponseEntity.status(200)
                .body(cartService.getCart(userId));
    }

    @PutMapping("/{userId}/{productId}")
    public ResponseEntity<Optional<Cart>> removeProductFromCart(
            @PathVariable String userId,
            @PathVariable String productId) {
        return ResponseEntity.status(200).body(
                cartService.removeProductFromCart(userId, productId)
        );
    }
}
