package com.astroloop.repository;

import com.astroloop.entity.CosmicCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CosmicCardRepository extends JpaRepository<CosmicCard, Long> {
    Optional<CosmicCard> findByShareId(String shareId);
    List<CosmicCard> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT COUNT(c) FROM CosmicCard c")
    long countAll();

    @Query("SELECT COUNT(c) FROM CosmicCard c WHERE c.createdAt >= :since")
    long countByCreatedAtAfter(LocalDateTime since);

    @Query("SELECT COALESCE(SUM(c.shareCount), 0) FROM CosmicCard c")
    long totalShares();
}
