package it.smartcommunitylabdhub.purchases.services;


import it.smartcommunitylabdhub.purchases.feigns.OpenFeignCatalogService;
import it.smartcommunitylabdhub.purchases.models.Cart;
import it.smartcommunitylabdhub.purchases.models.Item;
import it.smartcommunitylabdhub.purchases.models.Product;
import it.smartcommunitylabdhub.purchases.models.dtos.CartDTO;
import it.smartcommunitylabdhub.purchases.repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final OpenFeignCatalogService catalogService;

    public CartService(CartRepository cartRepository, OpenFeignCatalogService catalogService) {
        this.cartRepository = cartRepository;
        this.catalogService = catalogService;
    }


    public Optional<Cart> getCart(String userId) {

        return cartRepository.findByUserId(userId);
    }

    public Optional<Cart> updateCart(String userId, Cart cart) {
        return cartRepository.findByUserId(userId).map(
                c -> {
                    c.setItems(cart.getItems());
                    c.setTotalPrice(cart.getTotalPrice());
                    return cartRepository.save(cart);
                });
    }

    public Boolean deleteCart(String userId) {
        try {
            cartRepository.findByUserId(userId)
                    .ifPresent(cartRepository::delete);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public CartDTO addProductToCart(String userId, String productId, Integer quantity) {
        // Check if product is available from catalog
        Product product = catalogService.getProductFromCatalog(productId);

        // Check if product is available
        if (product.getAvailability() < quantity) {
            throw new RuntimeException("Product not available");
        }
        // check if user has a cart
        if (cartRepository.findByUserId(userId).isEmpty()) {
            // create a new cart
            Cart cart = cartRepository.save(Cart.builder()
                    .userId(userId)
                    .items(List.of((Item.builder()
                            .product(product)
                            .quantity(quantity)
                            .price(product.getPrice())
                            .build())))
                    .totalPrice(product.getPrice() * quantity)
                    .build());


            return CartDTO.builder()
                    .userId(userId)
                    .items(cart.getItems())
                    .totalPrice(cart.getTotalPrice())
                    .build();
        } else {
            // get cart
            Cart cart = cartRepository.findByUserId(userId).get();

            // check if product is already in cart
            Item item = cart.getItems().stream()
                    .filter(i -> i.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (item == null) {
                // add product to cart
                cart.getItems().add(Item.builder()
                        .product(product)
                        .quantity(quantity)
                        .price(product.getPrice())
                        .build());
                cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * quantity);
                cartRepository.save(cart);
            } else {
                // update quantity
                item.setQuantity(item.getQuantity() + quantity);
                item.setPrice(product.getPrice());
                cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * quantity);
                cartRepository.save(cart);
            }

            return CartDTO.builder()
                    .userId(userId)
                    .items(cart.getItems())
                    .totalPrice(cart.getTotalPrice())
                    .build();
        }
    }

    public Optional<Cart> removeProductFromCart(String userId, String productId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);

        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();

            cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

            double newTotal = cart.getItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();

            cart.setTotalPrice(newTotal);
            cartRepository.save(cart);

            return Optional.of(cart);
        } else {
            return Optional.empty();
        }
    }

}
