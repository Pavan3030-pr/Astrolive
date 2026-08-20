package com.astroloop.repository;

import com.astroloop.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT COUNT(c) FROM Consultation c WHERE c.status = 'COMPLETED'")
    long countCompleted();
}
