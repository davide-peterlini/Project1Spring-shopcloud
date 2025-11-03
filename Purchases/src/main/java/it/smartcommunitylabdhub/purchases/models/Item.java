package it.smartcommunitylabdhub.purchases.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // NON String id
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private Double price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    // Constructors
    public Item() {}
    
    public Item(String productId, Integer quantity, Double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
