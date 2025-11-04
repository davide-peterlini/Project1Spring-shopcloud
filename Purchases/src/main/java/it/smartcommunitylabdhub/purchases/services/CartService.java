package it.smartcommunitylabdhub.purchases.services;

import it.smartcommunitylabdhub.purchases.models.Cart;
import it.smartcommunitylabdhub.purchases.models.Item;
import it.smartcommunitylabdhub.purchases.models.dtos.CartDTO;
import it.smartcommunitylabdhub.purchases.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Optional<CartDTO> getCartByUserId(String userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        return cart.map(this::convertToDTO);
    }

    public CartDTO saveCart(CartDTO cartDTO) {
        Cart cart = convertToEntity(cartDTO);
        Cart savedCart = cartRepository.save(cart);
        return convertToDTO(savedCart);
    }

    public CartDTO updateCart(CartDTO cartDTO) {
        Optional<Cart> existingCart = cartRepository.findByUserId(cartDTO.getUserId());
        
        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            updateCartFromDTO(cart, cartDTO);
            Cart savedCart = cartRepository.save(cart);
            return convertToDTO(savedCart);
        } else {
            return saveCart(cartDTO);
        }
    }

    public void deleteCartByUserId(String userId) {
        cartRepository.deleteByUserId(userId);
    }

    public CartDTO addItemToCart(String userId, CartDTO.ItemDTO itemDTO) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
        if (cart.getId() == null) {
            cart.setUserId(userId);
        }

        // Check if item already exists in cart
        Optional<Item> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(itemDTO.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity
            Item item = existingItem.get();
            item.setQuantity(item.getQuantity() + itemDTO.getQuantity());
        } else {
            // Add new item
            Item newItem = new Item();
            newItem.setProductId(itemDTO.getProductId());
            newItem.setQuantity(itemDTO.getQuantity());
            newItem.setPrice(itemDTO.getPrice());
            cart.addItem(newItem);
        }

        // Recalculate total price
        calculateTotalPrice(cart);
        
        Cart savedCart = cartRepository.save(cart);
        return convertToDTO(savedCart);
    }

    public CartDTO removeItemFromCart(String userId, String productId) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.getItems().removeIf(item -> item.getProductId().equals(productId));
            calculateTotalPrice(cart);
            Cart savedCart = cartRepository.save(cart);
            return convertToDTO(savedCart);
        }
        
        throw new RuntimeException("Cart not found for user: " + userId);
    }

    private void calculateTotalPrice(Cart cart) {
        if (cart.getItems() != null) {
            double total = cart.getItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
            cart.setTotalPrice(total);
        } else {
            cart.setTotalPrice(0.0);
        }
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setTotalPrice(cart.getTotalPrice());
        
        if (cart.getItems() != null && !cart.getItems().isEmpty()) {
            List<CartDTO.ItemDTO> itemDTOs = cart.getItems().stream()
                    .map(this::convertItemToDTO)
                    .collect(Collectors.toList());
            dto.setItems(itemDTOs);
        } else {
            dto.setItems(new ArrayList<>());
        }
        
        return dto;
    }

    private Cart convertToEntity(CartDTO dto) {
        Cart cart = new Cart();
        if (dto.getId() != null) {
            cart.setId(dto.getId());
        }
        cart.setUserId(dto.getUserId());
        cart.setTotalPrice(dto.getTotalPrice());
        
        if (dto.getItems() != null) {
            List<Item> items = dto.getItems().stream()
                    .map(itemDTO -> convertItemToEntity(itemDTO, cart))
                    .collect(Collectors.toList());
            cart.setItems(items);
        }
        
        return cart;
    }

    private void updateCartFromDTO(Cart cart, CartDTO dto) {
        cart.setTotalPrice(dto.getTotalPrice());
        
        if (dto.getItems() != null) {
            cart.getItems().clear();
            List<Item> items = dto.getItems().stream()
                    .map(itemDTO -> convertItemToEntity(itemDTO, cart))
                    .collect(Collectors.toList());
            cart.getItems().addAll(items);
        }
    }

    private CartDTO.ItemDTO convertItemToDTO(Item item) {
        CartDTO.ItemDTO dto = new CartDTO.ItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }

    private Item convertItemToEntity(CartDTO.ItemDTO dto, Cart cart) {
        Item item = new Item();
        if (dto.getId() != null) {
            item.setId(dto.getId());
        }
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        item.setCart(cart);
        return item;
    }
}