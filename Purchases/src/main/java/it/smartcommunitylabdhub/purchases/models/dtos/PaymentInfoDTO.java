package it.smartcommunitylabdhub.purchases.models.dtos;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentInfoDTO {
    private String userId;
    private String orderId;
    private String paymentMethod;
    private CardDTO card;
}
