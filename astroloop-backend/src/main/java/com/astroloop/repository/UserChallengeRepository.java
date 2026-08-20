package com.astroloop.repository;

import com.astroloop.entity.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {
    List<UserChallenge> findByUserIdOrderByJoinedAtDesc(Long userId);
    Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);
    long countByUserIdAndCompletedTrue(Long userId);
}
