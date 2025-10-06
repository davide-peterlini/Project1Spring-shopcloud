package com.shopcloud.rating.service;

import com.shopcloud.rating.model.Rating;
import com.shopcloud.rating.model.RatingKey;
import com.shopcloud.rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository repo;

    public Rating saveRating(Long productId, Long userId, Rating rating) {
        rating.setId(new RatingKey(productId, userId));
        return repo.save(rating);
    }

    public List<Rating> getRatingsByProduct(Long productId) {
        return repo.findByIdProductId(productId);
    }

    public List<Map<String,Object>> getPopularProducts() {
        return repo.findAverageRatings().stream()
            .map(obj -> Map.of("productId", obj[0], "avgVote", obj[1]))
            .collect(Collectors.toList());
    }
}
