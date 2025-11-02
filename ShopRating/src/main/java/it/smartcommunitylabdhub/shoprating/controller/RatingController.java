package it.smartcommunitylabdhub.shoprating.controller;

import com.example.shoprating.entity.Rating;
import com.example.shoprating.service.RatingService;

import main.java.it.smartcommunitylabdhub.shoprating.dto.RatingRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // DTO per il body
    public record RatingRequest(int voto, String commento) {}

    @PostMapping("/{productId}/{userId}")
    public ResponseEntity<Rating> addOrUpdateRating(
            @PathVariable Long productId,
            @PathVariable Long userId,
            @RequestBody RatingRequest request) {

        Rating rating = ratingService.addOrUpdateRating(productId, userId, request);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Page<Rating>> getRatings(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ResponseEntity.ok(ratingService.getRatingsForProduct(productId, pageable));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Map<String, Object>>> getPopularProducts() {
        return ResponseEntity.ok(ratingService.getPopularProducts());
    }
}
