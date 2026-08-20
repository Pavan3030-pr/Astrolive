package com.astroloop.repository;

import com.astroloop.entity.DailyActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyActivityRepository extends JpaRepository<DailyActivity, Long> {
    List<DailyActivity> findByUserIdAndActivityDateOrderByCreatedAtDesc(Long userId, LocalDate date);
    List<DailyActivity> findByUserIdOrderByActivityDateDesc(Long userId);

    @Query("SELECT COUNT(DISTINCT da.user.id) FROM DailyActivity da WHERE da.activityDate = :date")
    long countDistinctUsersByDate(LocalDate date);
}
