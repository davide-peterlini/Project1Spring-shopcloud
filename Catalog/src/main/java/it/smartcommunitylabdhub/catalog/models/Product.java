package it.smartcommunitylabdhub.catalog.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    @NonNull
    private String code;
    private String title;
    private String category;
    private String description;
    private Double price;
    private Integer availability;
}
