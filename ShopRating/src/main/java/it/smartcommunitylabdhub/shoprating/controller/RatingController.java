package com.shopcloud.rating.controller;

import com.shopcloud.rating.model.Rating;
import com.shopcloud.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RatingController {

    @Autowired
    private RatingService service;

    @PostMapping("/ratings/{productId}/{userId}")
    public ResponseEntity<Rating> addRating(@PathVariable Long productId,
                                            @PathVariable Long userId,
                                            @RequestBody Rating rating) {
        return ResponseEntity.ok(service.saveRating(productId, userId, rating));
    }

    @GetMapping("/ratings/{productId}")
    public ResponseEntity<List<Rating>> getRatings(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getRatingsByProduct(productId));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Map<String,Object>>> getPopular() {
        return ResponseEntity.ok(service.getPopularProducts());
    }
}
