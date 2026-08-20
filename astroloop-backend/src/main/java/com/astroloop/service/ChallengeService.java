package com.astroloop.service;

import com.astroloop.dto.ChallengeResponse;
import com.astroloop.entity.Challenge;
import com.astroloop.entity.User;
import com.astroloop.entity.UserChallenge;
import com.astroloop.repository.ChallengeRepository;
import com.astroloop.repository.UserChallengeRepository;
import com.astroloop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserChallengeRepository userChallengeRepository;
    private final UserRepository userRepository;

    public List<ChallengeResponse> getChallenges(Long userId) {
        List<Challenge> challenges = challengeRepository.findByActiveTrue();
        return challenges.stream()
                .map(c -> mapToResponse(c, userId))
                .collect(Collectors.toList());
    }

    public ChallengeResponse joinChallenge(Long userId, Long challengeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

        UserChallenge existing = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId)
                .orElse(null);

        if (existing != null) {
            throw new IllegalStateException("Already joined this challenge");
        }

        UserChallenge userChallenge = UserChallenge.builder()
                .user(user)
                .challenge(challenge)
                .completed(false)
                .build();

        userChallenge = userChallengeRepository.save(userChallenge);
        return mapToResponse(challenge, userId);
    }

    public ChallengeResponse completeChallenge(Long userId, Long challengeId) {
        UserChallenge userChallenge = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found or not joined"));

        userChallenge.setCompleted(true);
        userChallenge.setCompletedAt(LocalDateTime.now());
        userChallenge.setScore(100);
        userChallengeRepository.save(userChallenge);

        return mapToResponse(userChallenge.getChallenge(), userId);
    }

    private ChallengeResponse mapToResponse(Challenge challenge, Long userId) {
        UserChallenge uc = userChallengeRepository.findByUserIdAndChallengeId(userId, challenge.getId())
                .orElse(null);

        return ChallengeResponse.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .category(challenge.getCategory())
                .rewardPoints(challenge.getRewardPoints())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .joined(uc != null)
                .completed(uc != null && uc.isCompleted())
                .score(uc != null ? uc.getScore() : null)
                .build();
    }
}
