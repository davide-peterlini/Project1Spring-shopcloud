package it.outdoor.payment.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private String id;
    private String userId;
    private List<Item> items;
    private Double totalPrice;
    private String status;
}