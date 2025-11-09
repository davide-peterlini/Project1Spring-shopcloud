package it.smartcommunitylabdhub.shoprating.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class RatingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int voto;

    @Column(length = 1000)
    private String commento;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public RatingRequest() {
        this.createdAt = LocalDateTime.now();
    }

    public RatingRequest(int voto, String commento) {
        this.voto = voto;
        this.commento = commento;
        this.createdAt = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
