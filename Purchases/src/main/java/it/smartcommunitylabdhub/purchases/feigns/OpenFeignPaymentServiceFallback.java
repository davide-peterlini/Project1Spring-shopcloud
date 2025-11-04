package it.smartcommunitylabdhub.purchases.feigns;

import it.smartcommunitylabdhub.purchases.models.dtos.PaymentDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.PaymentInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignPaymentServiceFallback implements OpenFeignPaymentService {
    
    @Override
    public PaymentInfoDTO processPayment(PaymentDTO paymentDTO) {
        PaymentInfoDTO fallbackResponse = new PaymentInfoDTO();
        fallbackResponse.setPaymentId("FALLBACK-" + System.currentTimeMillis());
        fallbackResponse.setStatus("FAILED");
        fallbackResponse.setMessage("Payment service is currently unavailable");
        fallbackResponse.setAmount(paymentDTO.getAmount());
        fallbackResponse.setCurrency(paymentDTO.getCurrency());
        return fallbackResponse;
    }
    
    @Override
    public PaymentInfoDTO getPaymentInfo(String paymentId) {
        PaymentInfoDTO fallbackResponse = new PaymentInfoDTO();
        fallbackResponse.setPaymentId(paymentId);
        fallbackResponse.setStatus("UNKNOWN");
        fallbackResponse.setMessage("Payment service is currently unavailable");
        return fallbackResponse;
    }
    
    @Override
    public PaymentInfoDTO refundPayment(String paymentId) {
        PaymentInfoDTO fallbackResponse = new PaymentInfoDTO();
        fallbackResponse.setPaymentId(paymentId);
        fallbackResponse.setStatus("FAILED");
        fallbackResponse.setMessage("Payment service is currently unavailable - refund failed");
        return fallbackResponse;
    }
}