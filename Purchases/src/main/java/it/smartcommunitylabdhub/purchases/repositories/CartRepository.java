package it.smartcommunitylabdhub.purchases.repositories;

import it.smartcommunitylabdhub.purchases.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserId(String userId);
}
