package it.smartcommunitylabdhub.shoprating.controller;

import it.smartcommunitylabdhub.shoprating.dto.RatingResponse;
import it.smartcommunitylabdhub.shoprating.service.RatingService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // GET /api/ratings/{productId}?page=0&size=10&sort=createdAt,desc
    @GetMapping("/{productId}")
    public Page<RatingResponse> getRatingsByProduct(
            @PathVariable @Min(1) Long productId,
            @PageableDefault(size = 10, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ratingService.getRatingsForProduct(productId, pageable);
    }
}
