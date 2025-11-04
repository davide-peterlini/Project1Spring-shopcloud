package it.smartcommunitylabdhub.purchases.controllers;

import it.smartcommunitylabdhub.purchases.models.dtos.CartDTO;
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
    public ResponseEntity<CartDTO> getCart(@PathVariable String userId) {
        Optional<CartDTO> cart = cartService.getCartByUserId(userId);
        if (cart.isPresent()) {
            return ResponseEntity.ok(cart.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable String userId, @RequestBody CartDTO cart) {
        try {
            cart.setUserId(userId);
            CartDTO updatedCart = cartService.updateCart(cart);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteCart(@PathVariable String userId) {
        try {
            cartService.deleteCartByUserId(userId);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartDTO> addProductToCart(
            @PathVariable String userId,
            @PathVariable String productId,
            @RequestParam Integer quantity,
            @RequestParam Double price) {
        try {
            CartDTO.ItemDTO itemDTO = new CartDTO.ItemDTO();
            itemDTO.setProductId(productId);
            itemDTO.setQuantity(quantity);
            itemDTO.setPrice(price);

            CartDTO updatedCart = cartService.addItemToCart(userId, itemDTO);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartDTO> removeProductFromCart(
            @PathVariable String userId,
            @PathVariable String productId) {
        try {
            CartDTO updatedCart = cartService.removeItemFromCart(userId, productId);
            return ResponseEntity.ok(updatedCart);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
