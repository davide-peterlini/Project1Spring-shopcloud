package it.outdoor.payment.models.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private String id;
    private String userId;
    private String orderId;
    private String paymentId;
    private Double amount;
    private String status;
}
