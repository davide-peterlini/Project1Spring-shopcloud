package it.smartcommunitylabdhub.catalog.repositories;

import it.smartcommunitylabdhub.catalog.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
}
