package it.smartcommunitylabdhub.shoprating.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id","user_id"}))
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int voto; // 1-5

    @Column(length = 1000)
    private String commento;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Rating() {}

    public Rating(Long productId, Long userId, int voto, String commento) {
        this.productId = productId;
        this.userId = userId;
        this.voto = voto;
        this.commento = commento;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public int getVoto() { return voto; }
    public void setVoto(int voto) { this.voto = voto; }
    public String getCommento() { return commento; }
    public void setCommento(String commento) { this.commento = commento; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}