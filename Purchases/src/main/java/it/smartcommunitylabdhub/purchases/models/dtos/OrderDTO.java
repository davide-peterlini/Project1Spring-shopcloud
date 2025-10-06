package it.smartcommunitylabdhub.purchases.models.dtos;


import it.smartcommunitylabdhub.purchases.models.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private String id;
    private String userId;
    private List<Item> items;
    private Double totalPrice;
    private String status;
    private PaymentDTO payment;
}
