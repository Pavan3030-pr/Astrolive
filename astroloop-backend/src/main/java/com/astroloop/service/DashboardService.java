package com.astroloop.service;

import com.astroloop.dto.DashboardResponse;
import com.astroloop.entity.AstrologyProfile;
import com.astroloop.entity.Streak;
import com.astroloop.repository.AchievementRepository;
import com.astroloop.repository.AstrologyProfileRepository;
import com.astroloop.repository.StreakRepository;
import com.astroloop.repository.UserChallengeRepository;
import com.astroloop.util.CosmicEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AstrologyProfileRepository profileRepository;
    private final StreakRepository streakRepository;
    private final UserChallengeRepository userChallengeRepository;
    private final AchievementRepository achievementRepository;

    public DashboardResponse getDashboard(Long userId) {
        AstrologyProfile profile = profileRepository.findByUserId(userId).orElse(null);
        if (profile == null) {
            return DashboardResponse.builder()
                    .hasProfile(false)
                    .greeting("Welcome to AstroLoop")
                    .build();
        }

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        String greeting;
        if (now.getHour() < 12) {
            greeting = "Good morning, " + profile.getName() + ".";
        } else if (now.getHour() < 17) {
            greeting = "Good afternoon, " + profile.getName() + ".";
        } else {
            greeting = "Good evening, " + profile.getName() + ".";
        }

        Streak streak = streakRepository.findByUserId(userId).orElse(null);
        int currentStreak = streak != null ? streak.getCurrentStreak() : 0;
        int longestStreak = streak != null ? streak.getLongestStreak() : 0;
        int totalCheckIns = streak != null ? streak.getTotalCheckIns() : 0;

        return DashboardResponse.builder()
                .hasProfile(true)
                .greeting(greeting)
                .cosmicBrief(CosmicEngine.getCosmicMessage(today, userId))
                .careerInsight(CosmicEngine.getCareerInsight(today, userId))
                .loveInsight(CosmicEngine.getLoveInsight(today, userId))
                .moneyInsight(CosmicEngine.getMoneyInsight(today, userId))
                .dailyQuestion(CosmicEngine.getDailyQuestion(today, userId))
                .energyScore(CosmicEngine.getEnergyScore(today, userId))
                .luckyElement(CosmicEngine.getLuckyElement(today, userId).getDisplayName())
                .currentStreak(currentStreak)
                .longestStreak(longestStreak)
                .totalCheckIns(totalCheckIns)
                .completedChallenges((int) userChallengeRepository.countByUserIdAndCompletedTrue(userId))
                .totalAchievements(achievementRepository.findByUserIdOrderByEarnedAtDesc(userId).size())
                .build();
    }
}
