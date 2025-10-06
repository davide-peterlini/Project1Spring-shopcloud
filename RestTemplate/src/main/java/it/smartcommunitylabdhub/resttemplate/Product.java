package it.smartcommunitylabdhub.resttemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String code;
    private String title;
    private String category;
    private String description;
    private Long price;
    private Integer availability;
}

