package it.smartcommunitylabdhub.purchases.repositories;

import it.smartcommunitylabdhub.purchases.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
