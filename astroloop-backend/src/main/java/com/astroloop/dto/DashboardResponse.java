package com.astroloop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DashboardResponse {
    private String greeting;
    private String cosmicBrief;
    private String careerInsight;
    private String loveInsight;
    private String moneyInsight;
    private String dailyQuestion;
    private int energyScore;
    private String luckyElement;
    private int currentStreak;
    private int longestStreak;
    private int totalCheckIns;
    private int completedChallenges;
    private int totalAchievements;
    private boolean hasProfile;
}
