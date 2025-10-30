package it.smartcommunitylabdhub.shoprating.repository;

import it.smartcommunitylabdhub.shoprating.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByProductIdAndUserId(Long productId, Long userId);

    Page<Rating> findByProductId(Long productId, Pageable pageable);

    // Calcola media voti per ogni prodotto
    @Query("SELECT r.productId AS productId, AVG(r.voto) AS avgRating FROM Rating r GROUP BY r.productId")
    List<Object[]> findAverageRatings();
}
