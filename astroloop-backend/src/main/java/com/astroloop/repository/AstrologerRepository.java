package com.astroloop.repository;

import com.astroloop.entity.Astrologer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AstrologerRepository extends JpaRepository<Astrologer, Long> {
    List<Astrologer> findByActiveTrue();

    @Query("SELECT a FROM Astrologer a WHERE a.active = true " +
           "AND (:expertise IS NULL OR a.expertise LIKE %:expertise%) " +
           "AND (:language IS NULL OR a.languages LIKE %:language%) " +
           "AND (:minRating IS NULL OR a.rating >= :minRating) " +
           "AND (:maxPrice IS NULL OR a.pricePerSession <= :maxPrice)")
    List<Astrologer> search(
        @Param("expertise") String expertise,
        @Param("language") String language,
        @Param("minRating") BigDecimal minRating,
        @Param("maxPrice") BigDecimal maxPrice
    );
}
