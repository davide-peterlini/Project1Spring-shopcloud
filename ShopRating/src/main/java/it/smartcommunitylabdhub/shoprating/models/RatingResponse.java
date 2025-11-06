package it.smartcommunitylabdhub.shoprating.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relazioni con Product e User (molto probabili nel tuo progetto)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int voto;

    @Column(length = 1000)
    private String commento;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Costruttori
    public Rating() {}

    public Rating(Product product, User user, int voto, String commento) {
        this.product = product;
        this.user = user;
        this.voto = voto;
        this.commento = commento;
        this.createdAt = LocalDateTime.now();
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
