package com.shopcloud.rating.repository;

import com.shopcloud.rating.model.Rating;
import com.shopcloud.rating.model.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingKey> {
    List<Rating> findByIdProductId(Long productId);

    @Query("SELECT r.id.productId AS productId, AVG(r.voto) AS avgVote " +
           "FROM Rating r GROUP BY r.id.productId ORDER BY avgVote DESC")
    List<Object[]> findAverageRatings();
}
