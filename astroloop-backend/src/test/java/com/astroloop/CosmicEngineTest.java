package com.astroloop;

import com.astroloop.enums.CosmicElement;
import com.astroloop.util.CosmicEngine;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CosmicEngineTest {

    @Test
    void getZodiacSign_gemini() {
        LocalDate dob = LocalDate.of(1995, 6, 15);
        assertEquals("Gemini", CosmicEngine.getZodiacSign(dob));
    }

    @Test
    void getZodiacSign_aries() {
        LocalDate dob = LocalDate.of(1998, 4, 5);
        assertEquals("Aries", CosmicEngine.getZodiacSign(dob));
    }

    @Test
    void getZodiacSign_scorpio() {
        LocalDate dob = LocalDate.of(1993, 11, 8);
        assertEquals("Scorpio", CosmicEngine.getZodiacSign(dob));
    }

    @Test
    void getZodiacSign_capricorn() {
        LocalDate dob = LocalDate.of(1990, 1, 10);
        assertEquals("Capricorn", CosmicEngine.getZodiacSign(dob));
    }

    @Test
    void getLuckyElement_returnsValidElement() {
        CosmicElement element = CosmicEngine.getLuckyElement(LocalDate.now(), 1L);
        assertNotNull(element);
        assertNotNull(element.getDisplayName());
        assertNotNull(element.getDescription());
    }

    @Test
    void getEnergyScore_withinRange() {
        int score = CosmicEngine.getEnergyScore(LocalDate.now(), 1L);
        assertTrue(score >= 55, "Score should be >= 55");
        assertTrue(score <= 98, "Score should be <= 98");
    }

    @Test
    void getEnergyScore_deterministic() {
        int score1 = CosmicEngine.getEnergyScore(LocalDate.of(2024, 1, 1), 42L);
        int score2 = CosmicEngine.getEnergyScore(LocalDate.of(2024, 1, 1), 42L);
        assertEquals(score1, score2, "Same date + user should produce same score");
    }

    @Test
    void getCosmicMessage_returnsNonNull() {
        String message = CosmicEngine.getCosmicMessage(LocalDate.now(), 1L);
        assertNotNull(message);
        assertFalse(message.isEmpty());
    }

    @Test
    void getCareerInsight_returnsNonNull() {
        String insight = CosmicEngine.getCareerInsight(LocalDate.now(), 1L);
        assertNotNull(insight);
        assertTrue(insight.length() > 10);
    }

    @Test
    void getLoveInsight_returnsNonNull() {
        String insight = CosmicEngine.getLoveInsight(LocalDate.now(), 1L);
        assertNotNull(insight);
        assertTrue(insight.length() > 10);
    }

    @Test
    void getMoneyInsight_returnsNonNull() {
        String insight = CosmicEngine.getMoneyInsight(LocalDate.now(), 1L);
        assertNotNull(insight);
        assertTrue(insight.length() > 10);
    }

    @Test
    void getDailyQuestion_returnsNonNull() {
        String question = CosmicEngine.getDailyQuestion(LocalDate.now(), 1L);
        assertNotNull(question);
        assertTrue(question.endsWith("?"));
    }

    @Test
    void calculateCompatibility_withinRange() {
        int score = CosmicEngine.calculateCompatibility(
                LocalDate.of(1995, 6, 15),
                LocalDate.of(1998, 3, 22)
        );
        assertTrue(score >= 60);
        assertTrue(score <= 98);
    }

    @Test
    void getStrengths_returnsThreeItems() {
        List<String> strengths = CosmicEngine.getStrengths(
                LocalDate.of(1995, 6, 15),
                LocalDate.of(1998, 3, 22)
        );
        assertEquals(3, strengths.size());
        strengths.forEach(s -> assertNotNull(s));
    }

    @Test
    void getFriction_returnsTwoItems() {
        List<String> friction = CosmicEngine.getFriction(
                LocalDate.of(1995, 6, 15),
                LocalDate.of(1998, 3, 22)
        );
        assertEquals(2, friction.size());
        friction.forEach(f -> assertNotNull(f));
    }

    @Test
    void getSuggestedAction_returnsNonNull() {
        String action = CosmicEngine.getSuggestedAction(
                LocalDate.of(1995, 6, 15),
                LocalDate.of(1998, 3, 22)
        );
        assertNotNull(action);
        assertTrue(action.length() > 10);
    }
}
