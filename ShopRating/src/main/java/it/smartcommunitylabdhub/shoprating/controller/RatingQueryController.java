package it.smartcommunitylabdhub.shoprating.controller;

import it.smartcommunitylabdhub.shoprating.models.dto.RatingResponseDTO;
import it.smartcommunitylabdhub.shoprating.service.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingQueryController {

    private final RatingService ratingService;

    public RatingQueryController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // GET /api/ratings/{productId}?page=0&size=10&sort=createdAt,desc
    @GetMapping("/{productId}")
    public Page<RatingResponseDTO> getRatingsByProduct(
            @PathVariable Long productId,
            @PageableDefault(size = 10, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ratingService.getRatingsForProductLight(productId, pageable);
    }
}
