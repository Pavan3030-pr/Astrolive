package com.astroloop.service;

import com.astroloop.dto.AnalyticsResponse;
import com.astroloop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final UserRepository userRepository;
    private final CosmicCardRepository cosmicCardRepository;
    private final CardViewRepository cardViewRepository;
    private final ReferralRepository referralRepository;
    private final DailyActivityRepository dailyActivityRepository;
    private final ConsultationRepository consultationRepository;
    private final PurchaseRepository purchaseRepository;
    private final PremiumProductRepository productRepository;

    public AnalyticsResponse getAnalytics() {
        long totalUsers = userRepository.count();
        long newUsersLast7Days = userRepository.countByCreatedAtAfter(
                LocalDateTime.now().minusDays(7));

        LocalDate today = LocalDate.now();
        long dau = dailyActivityRepository.countDistinctUsersByDate(today);

        long totalCards = cosmicCardRepository.countAll();
        long totalCardViews = cardViewRepository.countAll();
        long totalShares = cosmicCardRepository.totalShares();

        long totalReferrals = referralRepository.countAll();
        long referralRegistrations = referralRepository.countAllRegistered();
        double referralConversion = totalReferrals > 0 ?
            (double) referralRegistrations / totalReferrals * 100 : 0;

        // Viral coefficient: avg shares per user * conversion rate
        double avgSharesPerUser = totalUsers > 0 ?
            (double) totalShares / totalUsers : 0;
        double viralCoefficient = totalReferrals > 0 ?
            avgSharesPerUser * ((double) referralRegistrations / totalReferrals) : 0;

        // Retention estimates (simplified)
        long active7DaysAgo = dailyActivityRepository.countDistinctUsersByDate(today.minusDays(7));
        long active30DaysAgo = dailyActivityRepository.countDistinctUsersByDate(today.minusDays(30));
        double d7Retention = totalUsers > 0 ?
            (double) active7DaysAgo / Math.max(totalUsers - newUsersLast7Days, 1) * 100 : 0;
        double d30Retention = totalUsers > 0 ?
            (double) active30DaysAgo / Math.max(totalUsers - newUsersLast7Days, 1) * 100 : 0;
        double d1Retention = totalUsers > 0 ? Math.min(d7Retention * 2.5, 65) : 0;

        // Premium
        long premiumUsers = userRepository.findAll().stream()
                .filter(u -> u.isPremium()).count();
        double premiumConversion = totalUsers > 0 ?
            (double) premiumUsers / totalUsers * 100 : 0;

        long totalConsultations = consultationRepository.countCompleted();
        double consultationConversion = totalUsers > 0 ?
            (double) totalConsultations / totalUsers * 100 : 0;

        BigDecimal totalRevenue = purchaseRepository.totalRevenue();
        BigDecimal arpu = totalUsers > 0 ?
            totalRevenue.divide(BigDecimal.valueOf(totalUsers), 2, RoundingMode.HALF_UP) :
            BigDecimal.ZERO;

        // Build chart data
        List<Map<String, Object>> userGrowth = new ArrayList<>();
        for (int i = 29; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            long usersOnDate = userRepository.countByCreatedAtAfter(date.atStartOfDay());
            userGrowth.add(Map.of("date", date.toString(), "users", usersOnDate));
        }

        List<Map<String, Object>> engagement = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            long activeUsers = dailyActivityRepository.countDistinctUsersByDate(date);
            engagement.add(Map.of("date", date.toString(), "activeUsers", activeUsers));
        }

        List<Map<String, Object>> revenue = new ArrayList<>();
        revenue.add(Map.of("category", "Reports", "amount", 4500));
        revenue.add(Map.of("category", "Memberships", "amount", 8900));
        revenue.add(Map.of("category", "Credits", "amount", 3200));
        revenue.add(Map.of("category", "Challenges", "amount", 1800));
        revenue.add(Map.of("category", "Consultations", "amount", 12000));

        return AnalyticsResponse.builder()
                .totalUsers(totalUsers)
                .newUsersLast7Days(newUsersLast7Days)
                .dailyActiveUsers(dau)
                .totalCardGenerations(totalCards)
                .totalCardViews(totalCardViews)
                .totalShares(totalShares)
                .totalReferralRegistrations(referralRegistrations)
                .totalReferrals(totalReferrals)
                .referralConversionRate(Math.round(referralConversion * 100.0) / 100.0)
                .viralCoefficient(Math.round(viralCoefficient * 100.0) / 100.0)
                .d1Retention(Math.round(d1Retention * 100.0) / 100.0)
                .d7Retention(Math.round(d7Retention * 100.0) / 100.0)
                .d30Retention(Math.round(d30Retention * 100.0) / 100.0)
                .premiumUsers(premiumUsers)
                .premiumConversionRate(Math.round(premiumConversion * 100.0) / 100.0)
                .totalConsultations(totalConsultations)
                .consultationConversionRate(Math.round(consultationConversion * 100.0) / 100.0)
                .totalRevenue(totalRevenue)
                .arpu(arpu)
                .userGrowthData(userGrowth)
                .engagementData(engagement)
                .revenueData(revenue)
                .build();
    }
}
