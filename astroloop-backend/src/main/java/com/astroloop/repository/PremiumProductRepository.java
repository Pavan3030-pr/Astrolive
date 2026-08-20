package com.astroloop.repository;

import com.astroloop.entity.PremiumProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PremiumProductRepository extends JpaRepository<PremiumProduct, Long> {
    List<PremiumProduct> findByActiveTrue();
    List<PremiumProduct> findByCategoryAndActiveTrue(String category);
}
