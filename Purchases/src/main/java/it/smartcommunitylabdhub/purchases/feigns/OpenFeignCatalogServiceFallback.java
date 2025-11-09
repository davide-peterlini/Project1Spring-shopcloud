package it.smartcommunitylabdhub.purchases.feigns;

import it.smartcommunitylabdhub.purchases.models.Product;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignCatalogServiceFallback implements OpenFeignCatalogService {

    @Override
    public Product getProductFromCatalog(String id) {
        Product fallbackProduct = new Product();
        fallbackProduct.setExternalId(id);
        fallbackProduct.setName("Product Unavailable");
        fallbackProduct.setPrice(0.0);
        fallbackProduct.setStockQuantity(0);
        fallbackProduct.setCategory("Unknown");
        fallbackProduct.setDescription("Service unavailable");
        return fallbackProduct;
    }

    @Override
    public Boolean verifyProductAvailability(String id, Integer quantity) {
        return false;
    }

    @Override
    public void changeProductAvailability(String id, Integer quantity) {
        // Fallback: do nothing
    }
}
