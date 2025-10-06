package it.smartcommunitylabdhub.purchases.models.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CardDTO {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
}
