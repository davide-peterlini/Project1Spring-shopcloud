package it.outdoor.payment.services;


import it.outdoor.payment.feigns.OpenFeignPurchaseService;
import it.outdoor.payment.models.Order;
import it.outdoor.payment.models.Payment;
import it.outdoor.payment.models.dto.PaymentDTO;
import it.outdoor.payment.models.dto.PaymentInfoDTO;
import it.outdoor.payment.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    OpenFeignPurchaseService openFeignPurchaseService;

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentDTO pay(PaymentInfoDTO paymentInfoDTO) {
        // retrieve the order from the order service
        Optional<Order> order = openFeignPurchaseService
                .getOrder(
                        paymentInfoDTO.getUserId(),
                        paymentInfoDTO.getOrderId());

        // retrieve the payment from the payment service
        if (order.isPresent()) {
            Payment payment = new Payment();
            payment.setAmount(order.get().getTotalPrice());
            payment.setOrderId(paymentInfoDTO.getOrderId());
            payment.setUserId(paymentInfoDTO.getUserId());
            payment.setPaymentId(UUID.randomUUID().toString());
            payment.setStatus("paid");

            paymentRepository.save(payment);

            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId(payment.getId());
            paymentDTO.setUserId(payment.getUserId());
            paymentDTO.setOrderId(payment.getOrderId());
            paymentDTO.setPaymentId(payment.getPaymentId());
            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setStatus(payment.getStatus());
            return paymentDTO;

        } else {
            return PaymentDTO.builder().status("failed").build();
        }
    }
}
