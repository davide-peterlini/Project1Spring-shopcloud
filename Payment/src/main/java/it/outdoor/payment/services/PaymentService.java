package it.outdoor.payment.services;

import it.outdoor.payment.feigns.OpenFeignPurchaseService;
import it.outdoor.payment.models.Order;
import it.outdoor.payment.models.Payment;
import it.outdoor.payment.models.dto.PaymentDTO;
import it.outdoor.payment.models.dto.PaymentInfoDTO;
import it.outdoor.payment.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private OpenFeignPurchaseService openFeignPurchaseService;

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentDTO pay(PaymentInfoDTO paymentInfoDTO) {
        // Retrieve the order from the order service
        Optional<Order> orderOpt = openFeignPurchaseService
                .getOrder(
                        paymentInfoDTO.getUserId(),
                        paymentInfoDTO.getOrderId());

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            
            // Create new payment
            Payment payment = new Payment();
            payment.setAmount(order.getTotalPrice());
            payment.setOrderId(paymentInfoDTO.getOrderId());
            payment.setUserId(paymentInfoDTO.getUserId());
            payment.setPaymentId(UUID.randomUUID().toString());
            payment.setStatus("PAID");

            // Save payment
            Payment savedPayment = paymentRepository.save(payment);

            // Convert to DTO
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(savedPayment.getId());
            paymentDTO.setUserId(savedPayment.getUserId());
            paymentDTO.setOrderId(savedPayment.getOrderId());
            paymentDTO.setPaymentId(savedPayment.getPaymentId());
            paymentDTO.setAmount(savedPayment.getAmount());
            paymentDTO.setStatus(savedPayment.getStatus());
            paymentDTO.setCreatedAt(savedPayment.getCreatedAt());
            
            return paymentDTO;

        } else {
            return PaymentDTO.builder()
                    .status("FAILED")
                    .build();
        }
    }
    
    public Optional<PaymentDTO> getPaymentByOrderId(String orderId) {
        Optional<Payment> paymentOpt = paymentRepository.findByOrderId(orderId);
        
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(payment.getId());
            paymentDTO.setPaymentId(payment.getPaymentId());
            paymentDTO.setOrderId(payment.getOrderId());
            paymentDTO.setUserId(payment.getUserId());
            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setStatus(payment.getStatus());
            paymentDTO.setCreatedAt(payment.getCreatedAt());
            
            return Optional.of(paymentDTO);
        }
        
        return Optional.empty();
    }
}