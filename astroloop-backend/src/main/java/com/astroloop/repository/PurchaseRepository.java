package com.astroloop.repository;

import com.astroloop.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Purchase p WHERE p.status = 'COMPLETED'")
    BigDecimal totalRevenue();

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.status = 'COMPLETED'")
    long countCompleted();

    @Query("SELECT COUNT(DISTINCT p.user.id) FROM Purchase p WHERE p.status = 'COMPLETED'")
    long countUniquePurchasers();
}
