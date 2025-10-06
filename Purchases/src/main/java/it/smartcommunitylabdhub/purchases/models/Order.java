package it.smartcommunitylabdhub.purchases.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
@Builder
public class Order{

    @Id
    private String id;
    private String userId;
    private List<Item> items;
    private Double totalPrice;
    private String status;
}