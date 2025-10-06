package it.outdoor.payment.controllers;


import it.outdoor.payment.models.dto.PaymentDTO;
import it.outdoor.payment.models.dto.PaymentInfoDTO;
import it.outdoor.payment.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("")
    public ResponseEntity<PaymentDTO> pay(@RequestBody PaymentInfoDTO paymentInfoDTO) {
        return ResponseEntity.status(200).body(
                        paymentService.pay(paymentInfoDTO));
    }
}
