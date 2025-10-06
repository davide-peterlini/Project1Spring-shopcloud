package it.smartcommunitylabdhub.purchases.feigns;

import it.smartcommunitylabdhub.purchases.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "catalog", fallback = OpenFeignCatalogServiceFallback.class)
public interface OpenFeignCatalogService {

    @GetMapping("/api/products/{id}")
    Product getProductFromCatalog(@PathVariable String id);
    @GetMapping("/api/products/{id}/availability/{quantity}")
    Boolean verifyProductAvailability(@PathVariable String id, @PathVariable Integer quantity);
    @PutMapping("/api/products/{id}/availability/{quantity}")
    void changeProductAvailability(@PathVariable String id, @PathVariable Integer quantity);
}