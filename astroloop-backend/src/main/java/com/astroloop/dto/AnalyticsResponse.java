package com.astroloop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class AnalyticsResponse {
    private long totalUsers;
    private long newUsersLast7Days;
    private long dailyActiveUsers;
    private long totalCardGenerations;
    private long totalCardViews;
    private long totalShares;
    private long totalReferralRegistrations;
    private long totalReferrals;
    private double referralConversionRate;
    private double viralCoefficient;
    private double d1Retention;
    private double d7Retention;
    private double d30Retention;
    private long premiumUsers;
    private double premiumConversionRate;
    private long totalConsultations;
    private double consultationConversionRate;
    private BigDecimal totalRevenue;
    private BigDecimal arpu;
    private List<Map<String, Object>> userGrowthData;
    private List<Map<String, Object>> engagementData;
    private List<Map<String, Object>> revenueData;
}
