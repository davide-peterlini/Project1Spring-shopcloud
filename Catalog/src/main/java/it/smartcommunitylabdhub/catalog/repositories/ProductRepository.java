package it.smartcommunitylabdhub.catalog.repositories;

import it.smartcommunitylabdhub.catalog.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(String category);
}
