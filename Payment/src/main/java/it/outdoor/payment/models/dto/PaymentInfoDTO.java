package it.outdoor.payment.models.dto;


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
