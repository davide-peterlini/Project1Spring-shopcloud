package it.smartcommunitylabdhub.shoprating.service;

import it.smartcommunitylabdhub.shoprating.dto.RatingResponse;
import it.smartcommunitylabdhub.shoprating.entity.Rating;
import it.smartcommunitylabdhub.shoprating.repository.RatingRepository;
import main.java.it.smartcommunitylabdhub.shoprating.dto.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public Rating saveOrUpdateRating(Long productId, Long userId, RatingRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato"));

        Rating rating = ratingRepository.findByUserAndProduct(user, product)
                .orElse(new Rating());

        rating.setUser(user);
        rating.setProduct(product);
        rating.setVoto(request.getVoto());
        rating.setCommento(request.getCommento());

        return ratingRepository.save(rating);
    }

    public Page<Rating> getRatingsForProduct(Long productId, Pageable pageable) {
        return ratingRepository.findByProductId(productId, pageable);
    }

    public List<Map<String, Object>> getPopularProducts() {
        List<Object[]> results = ratingRepository.findAverageRatings();

        // Converti risultati in oggetti leggibili
        return results.stream()
                .map(r -> Map.of(
                        "productId", r[0],
                        "averageRating", ((Double) r[1])
                ))
                .collect(Collectors.toList());
    }

    public Page<RatingResponse> getRatingsForProductLight(Long productId, Pageable pageable) {
    // Riusa la tua query esistente, mappa agli unici due campi richiesti dall’API
    return ratingRepository.findByProductId(productId, pageable)
            .map(r -> new RatingResponse(r.getVoto(), r.getCommento()));
    }
}
