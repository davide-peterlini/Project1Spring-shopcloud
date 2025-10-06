package it.smartcommunitylabdhub.purchases.models.dtos;

import it.smartcommunitylabdhub.purchases.models.Item;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private String userId;
    private List<Item> items;
    private Double totalPrice;
}

