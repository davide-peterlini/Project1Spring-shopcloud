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
    private Long id; // Cambia da String a Long se necessario
    private String userId;
    private List<ItemDTO> items;
    private Double totalPrice;

    // Aggiungere ItemDTO se non esiste
    public static class ItemDTO {
        private Long id; // Nuovo campo
        private String productId;
        private Integer quantity;
        private Double price;

        // constructors, getters, setters...
    }
}

