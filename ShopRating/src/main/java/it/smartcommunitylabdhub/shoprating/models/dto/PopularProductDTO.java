package it.smartcommunitylabdhub.shoprating.models.dto;

public class PopularProductDTO {
    private String id;
    private String title;
    private Double price;
    private Double averageRating;

    public PopularProductDTO() {}

    public PopularProductDTO(String id, String title, Double price, Double averageRating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.averageRating = averageRating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
