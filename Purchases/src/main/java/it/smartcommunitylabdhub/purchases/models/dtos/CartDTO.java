package it.smartcommunitylabdhub.purchases.models.dtos;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CartDTO {
    
    private Long id;
    
    @NotNull
    private String userId;
    
    private List<ItemDTO> items;
    
    private Double totalPrice;

    // Constructors
    public CartDTO() {}

    public CartDTO(String userId, List<ItemDTO> items, Double totalPrice) {
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Inner class for ItemDTO
    public static class ItemDTO {
        private Long id;
        private String productId;
        private Integer quantity;
        private Double price;

        // Constructors
        public ItemDTO() {}

        public ItemDTO(String productId, Integer quantity, Double price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}