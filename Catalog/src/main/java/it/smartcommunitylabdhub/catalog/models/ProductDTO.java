package it.smartcommunitylabdhub.catalog.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String code;
    private String title;
    private String category;
    private String description;
    private Double price;
    private Integer availability;
}
