package com.astroloop.repository;

import com.astroloop.entity.CardView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CardViewRepository extends JpaRepository<CardView, Long> {

    @Query("SELECT COUNT(cv) FROM CardView cv WHERE cv.card.id = :cardId")
    long countByCardId(Long cardId);

    @Query("SELECT COUNT(cv) FROM CardView cv")
    long countAll();

    @Query("SELECT COUNT(cv) FROM CardView cv WHERE cv.viewedAt >= :since")
    long countByViewedAtAfter(LocalDateTime since);
}
