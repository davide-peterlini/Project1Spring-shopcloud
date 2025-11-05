package it.smartcommunitylabdhub.shoprating.entity;


import javax.persistence.*;
import lombok.*;


@Entity
@Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id","user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

      // Getters e setters
    public Long getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "product_id", nullable = false)
    private Long productId;


    @Column(name = "user_id", nullable = false)
    private Long userId;


    @Column(nullable = false)
    private int voto; // 1-5


    private String commento;
}