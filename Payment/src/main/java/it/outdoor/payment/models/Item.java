package it.outdoor.payment.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private Product product;
    private Integer quantity;
    private Double price;
}
