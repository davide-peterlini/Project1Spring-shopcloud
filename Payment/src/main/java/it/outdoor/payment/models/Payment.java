package it.outdoor.payment.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payment")
public class Payment {
    @Id
    private String id;
    private String userId;
    private String orderId;
    private String paymentId;
    private Double amount;
    private String status;
}
