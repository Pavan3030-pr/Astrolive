package com.astroloop.config;

import com.astroloop.entity.*;
import com.astroloop.enums.*;
import com.astroloop.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AstrologyProfileRepository profileRepository;
    private final ChallengeRepository challengeRepository;
    private final AstrologerRepository astrologerRepository;
    private final PremiumProductRepository productRepository;
    private final StreakRepository streakRepository;
    private final CosmicCardRepository cosmicCardRepository;
    private final ReferralRepository referralRepository;
    private final PurchaseRepository purchaseRepository;
    private final AchievementRepository achievementRepository;
    private final DailyActivityRepository dailyActivityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        log.info("Seeding database with demo data...");

        // Create demo users
        User user1 = createUser("demo@astroloop.com", "demo123", "Priya", "Sharma", "ASTRO-PRIYA-2024");
        User user2 = createUser("aria@example.com", "demo123", "Aria", "Patel", "ASTRO-ARIA-2024");
        User user3 = createUser("dev@example.com", "demo123", "Dev", "Kumar", "ASTRO-DEV-2024");
        user2.setReferredBy(user1);
        userRepository.save(user2);
        user3.setReferredBy(user1);
        userRepository.save(user3);

        // Create profiles
        createProfile(user1, "Priya", LocalDate.of(1995, 6, 15), null, "Mumbai, India", Interest.CAREER, "Gemini");
        createProfile(user2, "Aria", LocalDate.of(1998, 3, 22), null, "Delhi, India", Interest.LOVE, "Aries");
        createProfile(user3, "Dev", LocalDate.of(1993, 11, 8), null, "Bangalore, India", Interest.MONEY, "Scorpio");

        // Create streaks
        createStreak(user1, 7, 14, LocalDate.now(), 21);
        createStreak(user2, 3, 5, LocalDate.now(), 10);
        createStreak(user3, 1, 3, LocalDate.now().minusDays(1), 5);

        // Create challenges
        createChallenges();

        // Create astrologers
        createAstrologers();

        // Create premium products
        createPremiumProducts();

        // Create achievements
        createAchievement(user1, "First Light", "⭐", "Generated your first Cosmic Card", 50);
        createAchievement(user1, "Social Butterfly", "🦋", "Shared 10 Cosmic Cards", 100);
        createAchievement(user1, "Streak Master", "🔥", "Maintained a 7-day streak", 200);
        createAchievement(user2, "First Light", "⭐", "Generated your first Cosmic Card", 50);

        // Create referrals
        Referral ref1 = Referral.builder()
                .referrer(user1)
                .visitor(user2)
                .source("cosmic-card-share")
                .converted(true)
                .registered(true)
                .build();
        referralRepository.save(ref1);

        Referral ref2 = Referral.builder()
                .referrer(user1)
                .visitor(user3)
                .source("cosmic-card-share")
                .converted(true)
                .registered(true)
                .build();
        referralRepository.save(ref2);

        // Create sample purchases
        PremiumProduct report = productRepository.findByActiveTrue().stream()
                .filter(p -> p.getName().contains("Premium Cosmic Report"))
                .findFirst().orElse(null);
        if (report != null) {
            Purchase purchase = Purchase.builder()
                    .user(user1)
                    .product(report)
                    .amount(report.getPrice())
                    .simulated(true)
                    .status("COMPLETED")
                    .build();
            purchaseRepository.save(purchase);
            user1.setPremium(true);
            userRepository.save(user1);
        }

        log.info("Database seeding completed successfully!");
    }

    private User createUser(String email, String password, String firstName, String lastName, String referralCode) {
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .firstName(firstName)
                .lastName(lastName)
                .referralCode(referralCode)
                .active(true)
                .premium(false)
                .tier(PremiumTier.BASIC)
                .build();
        return userRepository.save(user);
    }

    private void createProfile(User user, String name, LocalDate dob, java.time.LocalTime tob, String place, Interest interest, String zodiac) {
        AstrologyProfile profile = AstrologyProfile.builder()
                .user(user)
                .name(name)
                .dateOfBirth(dob)
                .timeOfBirth(tob)
                .placeOfBirth(place)
                .primaryInterest(interest)
                .zodiacSign(zodiac)
                .build();
        profileRepository.save(profile);
    }

    private void createStreak(User user, int current, int longest, LocalDate lastCheckIn, int total) {
        Streak streak = Streak.builder()
                .user(user)
                .currentStreak(current)
                .longestStreak(longest)
                .lastCheckInDate(lastCheckIn)
                .totalCheckIns(total)
                .build();
        streakRepository.save(streak);
    }

    private void createChallenges() {
        Challenge c1 = Challenge.builder()
                .title("Share the Stars")
                .description("Generate and share a Cosmic Card with friends. Watch as your cosmic energy spreads!")
                .category("Social")
                .rewardPoints(100)
                .startDate(LocalDate.now().minusDays(3))
                .endDate(LocalDate.now().plusDays(4))
                .active(true)
                .premium(false)
                .build();
        challengeRepository.save(c1);

        Challenge c2 = Challenge.builder()
                .title("Cosmic Explorer")
                .description("Try Cosmic Match with 3 different friends this week and discover your compatibility!")
                .category("Discovery")
                .rewardPoints(150)
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(6))
                .active(true)
                .premium(false)
                .build();
        challengeRepository.save(c2);

        Challenge c3 = Challenge.builder()
                .title("Seven Day Streak")
                .description("Check in for 7 consecutive days and unlock exclusive cosmic insights.")
                .category("Retention")
                .rewardPoints(200)
                .startDate(LocalDate.now().minusDays(5))
                .endDate(LocalDate.now().plusDays(9))
                .active(true)
                .premium(false)
                .build();
        challengeRepository.save(c3);

        Challenge c4 = Challenge.builder()
                .title("Premium Insights")
                .description("Unlock the Premium Cosmic Report and dive deep into your cosmic blueprint.")
                .category("Premium")
                .rewardPoints(300)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .active(true)
                .premium(true)
                .build();
        challengeRepository.save(c4);

        Challenge c5 = Challenge.builder()
                .title("Stargazer")
                .description("Generate 5 Cosmic Cards in one week and become a true stargazer.")
                .category("Engagement")
                .rewardPoints(125)
                .startDate(LocalDate.now().minusDays(2))
                .endDate(LocalDate.now().plusDays(5))
                .active(true)
                .premium(false)
                .build();
        challengeRepository.save(c5);
    }

    private void createAstrologers() {
        astrologerRepository.save(Astrologer.builder()
                .name("Dr. Meera Joshi")
                .avatarUrl("/avatars/meera.jpg")
                .bio("With over 15 years of Vedic astrology experience, Dr. Meera provides deep, accurate insights into career and life path decisions.")
                .verified(true)
                .expertise("Vedic Astrology, Career Guidance, Kundli Analysis")
                .languages("Hindi, English, Marathi")
                .experienceYears(15)
                .rating(new BigDecimal("4.9"))
                .pricePerSession(new BigDecimal("499"))
                .availability("Mon-Sat, 10AM-6PM")
                .totalSessions(2840)
                .active(true)
                .build());

        astrologerRepository.save(Astrologer.builder()
                .name("Pandit Ravi Shankar")
                .avatarUrl("/avatars/ravi.jpg")
                .bio("Specializing in love and relationship astrology, Pandit Ravi has helped thousands find harmony in their relationships.")
                .verified(true)
                .expertise("Relationship Astrology, Love Compatibility, Remedies")
                .languages("Hindi, Tamil, Telugu")
                .experienceYears(20)
                .rating(new BigDecimal("4.8"))
                .pricePerSession(new BigDecimal("599"))
                .availability("Mon-Fri, 9AM-7PM")
                .totalSessions(3200)
                .active(true)
                .build());

        astrologerRepository.save(Astrologer.builder()
                .name("Guru Ananda Krishnan")
                .avatarUrl("/avatars/ananda.jpg")
                .bio("Expert in financial astrology and business timing. Guru Ananda helps entrepreneurs align their ventures with cosmic cycles.")
                .verified(true)
                .expertise("Financial Astrology, Business Timing, Investment Guidance")
                .languages("English, Tamil, Malayalam")
                .experienceYears(12)
                .rating(new BigDecimal("4.7"))
                .pricePerSession(new BigDecimal("399"))
                .availability("Tue-Sat, 11AM-8PM")
                .totalSessions(1560)
                .active(true)
                .build());

        astrologerRepository.save(Astrologer.builder()
                .name("Astrologer Priya Menon")
                .avatarUrl("/avatars/priya_m.jpg")
                .bio("A modern astrologer blending Western and Eastern techniques for holistic life guidance.")
                .verified(true)
                .expertise("Western Astrology, Numerology, Palmistry")
                .languages("English, Hindi, Malayalam")
                .experienceYears(8)
                .rating(new BigDecimal("4.6"))
                .pricePerSession(new BigDecimal("349"))
                .availability("Mon-Thu, 10AM-5PM")
                .totalSessions(980)
                .active(true)
                .build());

        astrologerRepository.save(Astrologer.builder()
                .name("Shri Venkatesh Rao")
                .avatarUrl("/avatars/venkatesh.jpg")
                .bio("Traditional Vedic astrologer with expertise in muhurta, electional astrology, and annual predictions.")
                .verified(true)
                .expertise("Vedic Astrology, Muhurta, Annual Predictions")
                .languages("Kannada, Hindi, English")
                .experienceYears(25)
                .rating(new BigDecimal("4.9"))
                .pricePerSession(new BigDecimal("699"))
                .availability("Mon-Sat, 8AM-2PM")
                .totalSessions(5100)
                .active(true)
                .build());

        astrologerRepository.save(Astrologer.builder()
                .name("Nisha Kapoor")
                .avatarUrl("/avatars/nisha.jpg")
                .bio("Tarot reader and cosmic energy healer combining astrology with intuitive guidance for personal transformation.")
                .verified(false)
                .expertise("Tarot Reading, Energy Healing, Chakra Balancing")
                .languages("English, Hindi, Punjabi")
                .experienceYears(5)
                .rating(new BigDecimal("4.4"))
                .pricePerSession(new BigDecimal("299"))
                .availability("Wed-Sun, 12PM-8PM")
                .totalSessions(420)
                .active(true)
                .build());
    }

    private void createPremiumProducts() {
        productRepository.save(PremiumProduct.builder()
                .name("Premium Cosmic Report")
                .description("A comprehensive 20-page personalized cosmic report analyzing your birth chart, current transits, and upcoming cosmic influences across career, love, and finance.")
                .price(new BigDecimal("199"))
                .category("report")
                .tier("PLUS")
                .active(true)
                .build());

        productRepository.save(PremiumProduct.builder()
                .name("Premium Compatibility Report")
                .description("Detailed compatibility analysis with your partner covering emotional, intellectual, physical, and spiritual alignment across 8 dimensions.")
                .price(new BigDecimal("299"))
                .category("report")
                .tier("PLUS")
                .active(true)
                .build());

        productRepository.save(PremiumProduct.builder()
                .name("Monthly Cosmic Membership")
                .description("Unlock all premium features including exclusive daily insights, advanced Cosmic Match, priority astrologer booking, and unlimited Cosmic Cards.")
                .price(new BigDecimal("299"))
                .category("membership")
                .tier("PRO")
                .active(true)
                .build());

        productRepository.save(PremiumProduct.builder()
                .name("Consultation Credits (5 Pack)")
                .description("Pre-purchase 5 consultation credits and save 20%. Credits are valid for any astrologer session type.")
                .price(new BigDecimal("999"))
                .category("credits")
                .tier("BASIC")
                .active(true)
                .build());

        productRepository.save(PremiumProduct.builder()
                .name("Premium Challenge Pack")
                .description("Unlock exclusive premium challenges with higher reward points and unique cosmic achievements.")
                .price(new BigDecimal("149"))
                .category("challenge")
                .tier("PLUS")
                .active(true)
                .build());
    }

    private void createAchievement(User user, String name, String icon, String desc, int points) {
        Achievement a = Achievement.builder()
                .user(user)
                .badgeName(name)
                .badgeIcon(icon)
                .description(desc)
                .points(points)
                .build();
        achievementRepository.save(a);
    }
}
