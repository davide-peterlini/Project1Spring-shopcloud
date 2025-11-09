package it.smartcommunitylabdhub.shoprating.service;

import it.smartcommunitylabdhub.shoprating.entity.Rating;
import it.smartcommunitylabdhub.shoprating.models.dto.RatingResponseDTO;
import it.smartcommunitylabdhub.shoprating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RatingService {

        @Autowired
        private final RatingRepository ratingRepository;

        public RatingService(RatingRepository ratingRepository) {
                this.ratingRepository = ratingRepository;
        }

        public Rating saveOrUpdateRating(Long productId, Long userId, int voto, String commento) {
                Optional<Rating> existing = ratingRepository.findByProductIdAndUserId(productId, userId);
                Rating r = existing.orElseGet(() -> new Rating(productId, userId, voto, commento));
                r.setProductId(productId);
                r.setUserId(userId);
                r.setVoto(voto);
                r.setCommento(commento);
                return ratingRepository.save(r);
        }

        public Page<Rating> getRatingsForProduct(Long productId, Pageable pageable) {
                return ratingRepository.findByProductId(productId, pageable);
        }

        public List<Map<String, Object>> getPopularProducts() {
                List<Object[]> results = ratingRepository.findAverageRatings();
                return results.stream()
                                .map(r -> Map.of(
                                                "productId", r[0],
                                                "averageRating", ((Double) r[1])
                                ))
                                .collect(Collectors.toList());
        }

        public Page<RatingResponseDTO> getRatingsForProductLight(Long productId, Pageable pageable) {
                return ratingRepository.findByProductId(productId, pageable)
                                .map(rt -> new RatingResponseDTO(rt.getVoto(), rt.getCommento()));
        }
}
