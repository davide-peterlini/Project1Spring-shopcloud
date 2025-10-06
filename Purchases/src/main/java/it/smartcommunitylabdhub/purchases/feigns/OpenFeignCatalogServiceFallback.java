package it.smartcommunitylabdhub.purchases.feigns;


import it.smartcommunitylabdhub.purchases.models.Product;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignCatalogServiceFallback {

    Product getProductFromCatalog(String id) {
        return Product.builder().build();
    }

    boolean verifyProductAvailability(String id, Integer quantity) {
        return false;
    }

    void changeProductAvailability(String id, Integer quantity) {
    }
}
