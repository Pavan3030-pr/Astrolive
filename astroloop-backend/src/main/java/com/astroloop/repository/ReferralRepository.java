package com.astroloop.repository;

import com.astroloop.entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {
    List<Referral> findByReferrerId(Long referrerId);

    @Query("SELECT COUNT(r) FROM Referral r WHERE r.referrer.id = :referrerId")
    long countByReferrerId(Long referrerId);

    @Query("SELECT COUNT(r) FROM Referral r WHERE r.referrer.id = :referrerId AND r.registered = true")
    long countRegisteredByReferrerId(Long referrerId);

    @Query("SELECT COUNT(r) FROM Referral r WHERE r.registered = true")
    long countAllRegistered();

    @Query("SELECT COUNT(r) FROM Referral r")
    long countAll();
}
