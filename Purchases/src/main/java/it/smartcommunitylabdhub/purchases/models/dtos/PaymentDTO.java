package it.smartcommunitylabdhub.purchases.models.dtos;


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
