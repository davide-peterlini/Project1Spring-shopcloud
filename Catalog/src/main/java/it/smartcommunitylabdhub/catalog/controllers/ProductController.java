package it.smartcommunitylabdhub.catalog.controllers;


import it.smartcommunitylabdhub.catalog.models.ProductDTO;
import it.smartcommunitylabdhub.catalog.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    //    List products: /api/products
    @GetMapping("")
    public List<ProductDTO> getAllProducts() throws InterruptedException {
        return productService.getAllProducts();
    }

    //    Get product by Id: /api/products/{id}
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id).orElse(null);
    }


    //    Search by category: /api/products/category/{category}
    @GetMapping("/category/{category}")
    public List<ProductDTO> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    //   Create product: POST /api/products
    @PostMapping("")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    //    Change availability: PUT /api/products/{id}/availability/{value}
    @PutMapping("/{id}/availability/{value}")
    public ProductDTO changeAvailability(@PathVariable Long id,
                                         @PathVariable Integer value) {
        return productService.changeAvailability(id, value);
    }
}

