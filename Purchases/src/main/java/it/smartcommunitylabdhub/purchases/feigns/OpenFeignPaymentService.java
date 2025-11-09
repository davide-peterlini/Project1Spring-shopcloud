package it.smartcommunitylabdhub.purchases.feigns;

import it.smartcommunitylabdhub.purchases.models.dtos.PaymentDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.PaymentInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service", fallback = OpenFeignPaymentServiceFallback.class)
public interface OpenFeignPaymentService {
    
    @PostMapping("/api/payments/process")
    PaymentInfoDTO processPayment(@RequestBody PaymentDTO paymentDTO);
    
    @GetMapping("/api/payments/{paymentId}")
    PaymentInfoDTO getPaymentInfo(@PathVariable("paymentId") String paymentId);
    
    @PostMapping("/api/payments/{paymentId}/refund")
    PaymentInfoDTO refundPayment(@PathVariable("paymentId") String paymentId);
}