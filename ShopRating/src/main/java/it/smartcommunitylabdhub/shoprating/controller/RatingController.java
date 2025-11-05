package it.smartcommunitylabdhub.shoprating.controller;

import it.smartcommunitylabdhub.shoprating.entity.Rating;
import it.smartcommunitylabdhub.shoprating.service.RatingService;
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

    // DTO per il body (compatibile con Java 11)
    public static class RatingRequest {
        private int voto;
        private String commento;

        public RatingRequest() {}

        public RatingRequest(int voto, String commento) {
            this.voto = voto;
            this.commento = commento;
        }

        public int getVoto() { return voto; }
        public void setVoto(int voto) { this.voto = voto; }
        public String getCommento() { return commento; }
        public void setCommento(String commento) { this.commento = commento; }
    }

    @PostMapping("/{productId}/{userId}")
    public ResponseEntity<Rating> addOrUpdateRating(
            @PathVariable Long productId,
            @PathVariable Long userId,
            @RequestBody RatingRequest request) {

    Rating rating = ratingService.saveOrUpdateRating(productId, userId, request.getVoto(), request.getCommento());
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
