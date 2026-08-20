package com.astroloop.util;

import com.astroloop.enums.CosmicElement;
import com.astroloop.enums.Interest;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

public class CosmicEngine {

    private static final String[] COSMIC_MESSAGES = {
        "The stars align to bring you clarity and purpose today.",
        "A powerful cosmic energy surrounds you — embrace it fully.",
        "Today's celestial alignment favors bold decisions.",
        "The universe is preparing something beautiful for you.",
        "Your inner light shines brighter than the North Star today.",
        "Cosmic forces are clearing a path for new opportunities.",
        "The moon's energy amplifies your natural intuition today.",
        "Saturn's influence brings discipline and lasting progress.",
        "Jupiter expands your horizons — look beyond the familiar.",
        "Venus adds warmth and charm to all your interactions.",
        "Mercury boosts your communication — speak your truth.",
        "Mars energizes your drive — channel it wisely today.",
        "Neptune deepens your spiritual awareness and empathy.",
        "Uranus brings unexpected breakthroughs and fresh perspectives.",
        "Your cosmic blueprint reveals hidden strengths waiting to surface."
    };

    private static final String[] CAREER_INSIGHTS = {
        "A new professional opportunity may present itself by midweek. Stay open to unexpected connections.",
        "Your analytical skills are heightened today. Perfect time for strategic planning and important decisions.",
        "Collaboration energy is strong. Consider reaching out to a mentor or colleague for fresh perspectives.",
        "A creative breakthrough awaits. Trust your instincts on that project you've been contemplating.",
        "Financial rewards follow consistent effort. Stay focused on your long-term career vision.",
        "Leadership energy surrounds you today. Take initiative and others will follow.",
        "A conversation with a colleague could open doors you didn't know existed.",
        "Your dedication is building toward something significant. Patience will be rewarded.",
        "Trust your expertise today. You know more than you think you do.",
        "Network authentically today — a genuine connection could change your career trajectory."
    };

    private static final String[] LOVE_INSIGHTS = {
        "Venus graces your relationship sector today. Express your feelings with confidence.",
        "Deep emotional connections are favored. Quality time with loved ones brings joy.",
        "Communication clarity in relationships improves. Address what's been left unsaid.",
        "Your charm is magnetic today. Use it to strengthen bonds and heal old wounds.",
        "A small gesture of kindness will have a big impact on someone special.",
        "Romance is in the air. Stay open to unexpected connections and rekindled sparks.",
        "Your emotional intelligence is your superpower today. Lead with empathy.",
        "Partnership energy is strong. Make decisions together for the best outcomes.",
        "Self-love is the foundation. Take time for yourself before giving to others.",
        "Heart-to-heart conversations are favored. Create space for honest dialogue."
    };

    private static final String[] MONEY_INSIGHTS = {
        "Jupiter's influence brings financial luck. Be mindful with investments and purchases.",
        "A practical approach to finances yields the best results today. Review your budget.",
        "New income streams may emerge. Stay alert to opportunities in your network.",
        "Today favors long-term financial planning over quick gains. Think strategically.",
        "Your financial intuition is sharp. Trust your gut on money decisions.",
        "Generosity creates abundance. Consider sharing your resources — it will return tenfold.",
        "Avoid impulsive spending today. The stars favor patience in financial matters.",
        "An unexpected expense may arise. Having an emergency fund will bring peace of mind.",
        "Investment in knowledge pays the best interest. Consider learning a new skill.",
        "Financial breakthroughs come from consistent habits, not one-time events."
    };

    private static final String[] DAILY_QUESTIONS = {
        "What cosmic message would you share with your younger self?",
        "If the stars could grant one wish today, what would you ask for?",
        "What area of your life needs the most cosmic energy right now?",
        "How would you describe your current energy level in one word?",
        "What is one thing you're grateful for in this cosmic moment?",
        "If today were governed by a constellation, which would it be?",
        "What cosmic lesson have you recently learned?",
        "How will you align your actions with the universe today?",
        "What celestial quality do you most admire in yourself?",
        "If you could harness one planetary energy today, which would it be?"
    };

    private static final String[] STRENGTHS = {
        "Deep emotional connection and mutual understanding",
        "Complementary communication styles that create harmony",
        "Shared values and life goals create a strong foundation",
        "Natural chemistry and magnetic attraction",
        "Mutual respect and admiration for each other's strengths",
        "Ability to balance each other's weaknesses with strengths",
        "Shared sense of humor and joy in simple moments",
        "Spiritual and emotional alignment on a deep level"
    };

    private static final String[] FRICTION = {
        "Different approaches to conflict resolution may cause tension",
        "Communication styles may occasionally clash",
        "Balancing independence with togetherness requires attention",
        "Different energy levels may need harmonizing",
        "Varying approaches to finances could create discussions",
        "Work-life balance preferences may differ"
    };

    private static final String[] ACTIONS = {
        "Plan a meaningful shared experience this week to deepen your connection.",
        "Have an open conversation about your shared dreams for the future.",
        "Create a small daily ritual together — consistency builds cosmic bonds.",
        "Give each other space for individual growth while nurturing togetherness.",
        "Celebrate your differences — they are your greatest source of strength.",
        "Practice active listening today and let your partner feel truly heard."
    };

    private static final CosmicElement[] ELEMENTS = CosmicElement.values();

    public static CosmicElement getLuckyElement(LocalDate date, Long userId) {
        int index = (date.getDayOfYear() + userId.hashCode()) % ELEMENTS.length;
        return ELEMENTS[Math.abs(index)];
    }

    public static int getEnergyScore(LocalDate date, Long userId) {
        int seed = (date.toString().hashCode() + userId.hashCode()) % 100;
        return Math.max(55, Math.min(98, 60 + Math.abs(seed % 39)));
    }

    public static String getCosmicMessage(LocalDate date, Long userId) {
        int index = Math.abs((date.getDayOfYear() + userId.hashCode()) % COSMIC_MESSAGES.length);
        return COSMIC_MESSAGES[index];
    }

    public static String getCareerInsight(LocalDate date, Long userId) {
        int index = Math.abs((date.getDayOfYear() + userId.hashCode() + 1) % CAREER_INSIGHTS.length);
        return CAREER_INSIGHTS[index];
    }

    public static String getLoveInsight(LocalDate date, Long userId) {
        int index = Math.abs((date.getDayOfYear() + userId.hashCode() + 2) % LOVE_INSIGHTS.length);
        return LOVE_INSIGHTS[index];
    }

    public static String getMoneyInsight(LocalDate date, Long userId) {
        int index = Math.abs((date.getDayOfYear() + userId.hashCode() + 3) % MONEY_INSIGHTS.length);
        return MONEY_INSIGHTS[index];
    }

    public static String getDailyQuestion(LocalDate date, Long userId) {
        int index = Math.abs((date.getDayOfYear() + userId.hashCode()) % DAILY_QUESTIONS.length);
        return DAILY_QUESTIONS[index];
    }

    public static String getZodiacSign(LocalDate dateOfBirth) {
        int month = dateOfBirth.getMonthValue();
        int day = dateOfBirth.getDayOfMonth();
        return switch (month) {
            case 1 -> day < 20 ? "Capricorn" : "Aquarius";
            case 2 -> day < 19 ? "Aquarius" : "Pisces";
            case 3 -> day < 21 ? "Pisces" : "Aries";
            case 4 -> day < 20 ? "Aries" : "Taurus";
            case 5 -> day < 21 ? "Taurus" : "Gemini";
            case 6 -> day < 21 ? "Gemini" : "Cancer";
            case 7 -> day < 23 ? "Cancer" : "Leo";
            case 8 -> day < 23 ? "Leo" : "Virgo";
            case 9 -> day < 23 ? "Virgo" : "Libra";
            case 10 -> day < 23 ? "Libra" : "Scorpio";
            case 11 -> day < 22 ? "Scorpio" : "Sagittarius";
            case 12 -> day < 22 ? "Sagittarius" : "Capricorn";
            default -> "Unknown";
        };
    }

    public static int calculateCompatibility(LocalDate sign1, LocalDate sign2) {
        int seed = (sign1.hashCode() + sign2.hashCode()) % 40;
        return Math.max(60, Math.min(98, 65 + Math.abs(seed % 34)));
    }

    public static List<String> getStrengths(LocalDate s1, LocalDate s2) {
        int seed = Math.abs((s1.hashCode() + s2.hashCode()) % STRENGTHS.length);
        List<String> result = new ArrayList<>();
        result.add(STRENGTHS[seed % STRENGTHS.length]);
        result.add(STRENGTHS[(seed + 1) % STRENGTHS.length]);
        result.add(STRENGTHS[(seed + 2) % STRENGTHS.length]);
        return result;
    }

    public static List<String> getFriction(LocalDate s1, LocalDate s2) {
        int seed = Math.abs((s1.hashCode() + s2.hashCode()) % FRICTION.length);
        List<String> result = new ArrayList<>();
        result.add(FRICTION[seed % FRICTION.length]);
        result.add(FRICTION[(seed + 1) % FRICTION.length]);
        return result;
    }

    public static String getSuggestedAction(LocalDate s1, LocalDate s2) {
        int index = Math.abs((s1.hashCode() + s2.hashCode()) % ACTIONS.length);
        return ACTIONS[index];
    }
}
