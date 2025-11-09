package it.smartcommunitylabdhub.catalog.services;


import it.smartcommunitylabdhub.catalog.models.Product;
import it.smartcommunitylabdhub.catalog.models.ProductDTO;
import it.smartcommunitylabdhub.catalog.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //    List products: /api/products
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream().map(product ->
                        new ProductDTO(
                                product.getId(),
                                product.getCode(),
                                product.getTitle(),
                                product.getCategory(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getAvailability()))
                .toList();
    }

    //    Get product by Id: /api/products/{id}
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(product ->
                new ProductDTO(
                        product.getId(),
                        product.getCode(),
                        product.getTitle(),
                        product.getCategory(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getAvailability()
        ));
    }

    //    Search by category: /api/products/category/{category}
    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream().map(product ->
                        new ProductDTO(
                                product.getId(),
                                product.getCode(),
                                product.getTitle(),
                                product.getCategory(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getAvailability()))
                .toList();
    }

    //Create product: POST /api/products
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setCode(productDTO.getCode());
        product.setTitle(productDTO.getTitle());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setAvailability(productDTO.getAvailability());
        productRepository.save(product);


        productDTO.setId(product.getId());
        return productDTO;
    }

    //    Change availability: PUT /api/products/{id}/availability/{value}
    public ProductDTO changeAvailability(Long id, Integer value) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null) {
            return null;
        }
        product.setAvailability(value);
        productRepository.save(product);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setCode(product.getCode());
        productDTO.setTitle(product.getTitle());
        productDTO.setCategory(product.getCategory());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setAvailability(product.getAvailability());
        return productDTO;
    }


//    List products: /api/products
//    Get product by Id: /api/products/{id}
//    Search by category: /api/products/category/{category}
//    Create product: POST /api/products
//    Change availability: PUT /api/products/{id}/availability/{value}

}