package com.astroloop.service;

import com.astroloop.dto.StreakResponse;
import com.astroloop.entity.DailyActivity;
import com.astroloop.entity.Streak;
import com.astroloop.enums.ActivityType;
import com.astroloop.repository.DailyActivityRepository;
import com.astroloop.repository.StreakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StreakService {

    private final StreakRepository streakRepository;
    private final DailyActivityRepository dailyActivityRepository;

    public StreakResponse checkIn(Long userId) {
        LocalDate today = LocalDate.now();

        Streak streak = streakRepository.findByUserId(userId).orElse(null);
        if (streak == null) {
            streak = Streak.builder()
                    .user(null)
                    .currentStreak(1)
                    .longestStreak(1)
                    .lastCheckInDate(today)
                    .totalCheckIns(1)
                    .build();
        } else {
            if (streak.getLastCheckInDate() != null) {
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                        streak.getLastCheckInDate(), today);
                if (daysBetween == 1) {
                    streak.setCurrentStreak(streak.getCurrentStreak() + 1);
                } else if (daysBetween > 1) {
                    streak.setCurrentStreak(1);
                }
                // Same day = already checked in
            } else {
                streak.setCurrentStreak(1);
            }

            if (streak.getCurrentStreak() > streak.getLongestStreak()) {
                streak.setLongestStreak(streak.getCurrentStreak());
            }

            streak.setLastCheckInDate(today);
            streak.setTotalCheckIns(streak.getTotalCheckIns() + 1);
        }

        streak = streakRepository.save(streak);

        // Record activity
        DailyActivity activity = DailyActivity.builder()
                .user(null) // Set by controller
                .activityType(ActivityType.DAILY_CHECK_IN)
                .activityDate(today)
                .build();
        dailyActivityRepository.save(activity);

        return StreakResponse.builder()
                .currentStreak(streak.getCurrentStreak())
                .longestStreak(streak.getLongestStreak())
                .totalCheckIns(streak.getTotalCheckIns())
                .lastCheckInDate(streak.getLastCheckInDate())
                .checkedInToday(streak.getLastCheckInDate() != null &&
                    streak.getLastCheckInDate().equals(today))
                .build();
    }

    public StreakResponse getStreak(Long userId) {
        Streak streak = streakRepository.findByUserId(userId).orElse(null);
        if (streak == null) {
            return StreakResponse.builder()
                    .currentStreak(0)
                    .longestStreak(0)
                    .totalCheckIns(0)
                    .lastCheckInDate(null)
                    .checkedInToday(false)
                    .build();
        }

        return StreakResponse.builder()
                .currentStreak(streak.getCurrentStreak())
                .longestStreak(streak.getLongestStreak())
                .totalCheckIns(streak.getTotalCheckIns())
                .lastCheckInDate(streak.getLastCheckInDate())
                .checkedInToday(streak.getLastCheckInDate() != null &&
                    streak.getLastCheckInDate().equals(LocalDate.now()))
                .build();
    }
}
