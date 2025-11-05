package it.smartcommunitylabdhub.shoprating.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "popular_products")
public class PopularProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double price;

    @Column(name = "average_rating")
    private Double averageRating;

    public PopularProduct() {}

    public PopularProduct(String title, Double price, Double averageRating) {
        this.title = title;
        this.price = price;
        this.averageRating = averageRating;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    // equals & hashCode per confronti tra entità
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PopularProduct)) return false;
        PopularProduct that = (PopularProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
