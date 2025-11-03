package it.smartcommunitylabdhub.purchases.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // NON String id
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
    private List<Item> items;
    
    // Constructors
    public Order() {
        this.orderDate = LocalDateTime.now();
    }
    
    public Order(String userId, Double totalAmount, String status) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { 
        this.items = items;
        if (items != null) {
            items.forEach(item -> item.setOrder(this));
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}