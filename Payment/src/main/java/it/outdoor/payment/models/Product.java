package it.outdoor.payment.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private String id;
    private String code;
    private String title;
    private String category;
    private String description;
    private Double price;
    private Integer availability;
}