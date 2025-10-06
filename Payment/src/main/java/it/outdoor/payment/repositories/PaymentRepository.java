package it.outdoor.payment.repositories;

import it.outdoor.payment.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {
}
