package it.smartcommunitylabdhub.purchases.repositories;

import it.smartcommunitylabdhub.purchases.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.userId = :userId")
    Optional<Cart> findByUserIdWithItems(@Param("userId") String userId);
    
    Optional<Cart> findByUserId(String userId);
    
    @Transactional
    void deleteByUserId(String userId);
    
    boolean existsByUserId(String userId);
}
