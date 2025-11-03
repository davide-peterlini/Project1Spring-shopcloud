package it.smartcommunitylabdhub.purchases.models.dtos;


import it.smartcommunitylabdhub.purchases.models.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id; // Cambia da String a Long se necessario
    private String userId;
    private Double totalAmount;
    private String status;
    private LocalDateTime orderDate;
    private List<ItemDTO> items;
}
