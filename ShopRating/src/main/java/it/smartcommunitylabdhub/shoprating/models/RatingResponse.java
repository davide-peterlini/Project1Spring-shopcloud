package it.smartcommunitylabdhub.shoprating.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class RatingResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int voto;

    @Column(length = 1000)
    private String commento;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public RatingResponse() {}

    public RatingResponse(Long productId, Long userId, int voto, String commento) {
        this.productId = productId;
        this.userId = userId;
        this.voto = voto;
        this.commento = commento;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public Long getUserId() { return userId; }
    public int getVoto() { return voto; }
    public String getCommento() { return commento; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
