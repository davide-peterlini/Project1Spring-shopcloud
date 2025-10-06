package it.smartcommunitylabdhub.purchases.feigns;

import it.smartcommunitylabdhub.purchases.models.Product;
import it.smartcommunitylabdhub.purchases.models.dtos.PaymentDTO;
import it.smartcommunitylabdhub.purchases.models.dtos.PaymentInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "payment")
public interface OpenFeignPaymentService {
    @PostMapping("/api/payment")
    PaymentDTO doPayment(@RequestBody PaymentInfoDTO paymentInfoDTO);

}