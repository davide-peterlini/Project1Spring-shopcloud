package it.smartcommunitylabdhub.shoprating.service;

import com.example.shoprating.entity.Rating;
import com.example.shoprating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating saveOrUpdateRating(Long productId, Long userId, int voto, String commento) {
        Rating rating = ratingRepository.findByProductIdAndUserId(productId, userId)
                .orElse(new Rating());
        rating.setProductId(productId);
        rating.setUserId(userId);
        rating.setVoto(voto);
        rating.setCommento(commento);
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
}
