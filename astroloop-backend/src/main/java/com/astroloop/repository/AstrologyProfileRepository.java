package com.astroloop.repository;

import com.astroloop.entity.AstrologyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AstrologyProfileRepository extends JpaRepository<AstrologyProfile, Long> {
    Optional<AstrologyProfile> findByUserId(Long userId);
}
