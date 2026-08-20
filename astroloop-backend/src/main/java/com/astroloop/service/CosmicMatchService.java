package com.astroloop.service;

import com.astroloop.dto.CosmicMatchRequest;
import com.astroloop.dto.CosmicMatchResponse;
import com.astroloop.entity.AstrologyProfile;
import com.astroloop.repository.AstrologyProfileRepository;
import com.astroloop.util.CosmicEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CosmicMatchService {

    private final AstrologyProfileRepository profileRepository;

    public CosmicMatchResponse calculateMatch(Long userId, CosmicMatchRequest request) {
        AstrologyProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Please create your profile first"));

        LocalDate userSign = profile.getDateOfBirth();
        LocalDate partnerSign = request.getPartnerDateOfBirth();

        String userZodiac = CosmicEngine.getZodiacSign(userSign);
        String partnerZodiac = CosmicEngine.getZodiacSign(partnerSign);

        int overall = CosmicEngine.calculateCompatibility(userSign, partnerSign);
        int love = CosmicEngine.calculateCompatibility(
            userSign.plusDays(7), partnerSign.plusDays(3)) % 40 + 60;
        int communication = CosmicEngine.calculateCompatibility(
            userSign.plusDays(14), partnerSign.plusDays(11)) % 35 + 62;
        int lifeAlignment = CosmicEngine.calculateCompatibility(
            userSign.plusDays(21), partnerSign.plusDays(19)) % 38 + 58;

        love = Math.min(99, Math.max(60, love));
        communication = Math.min(99, Math.max(62, communication));
        lifeAlignment = Math.min(99, Math.max(58, lifeAlignment));

        List<String> strengths = CosmicEngine.getStrengths(userSign, partnerSign);
        List<String> friction = CosmicEngine.getFriction(userSign, partnerSign);
        String action = CosmicEngine.getSuggestedAction(userSign, partnerSign);

        String summary = String.format(
            "%s (%s) and %s (%s) share a cosmic connection rated at %d%%. " +
            "Your celestial energies create a dynamic that blends %s energy with %s qualities.",
            profile.getName(), userZodiac,
            request.getPartnerName() != null ? request.getPartnerName() : "your partner", partnerZodiac,
            overall,
            userZodiac, partnerZodiac
        );

        return CosmicMatchResponse.builder()
                .user1Name(profile.getName())
                .user2Name(request.getPartnerName() != null ? request.getPartnerName() : "Your Partner")
                .user1Sign(userZodiac)
                .user2Sign(partnerZodiac)
                .overallScore(overall)
                .loveScore(love)
                .communicationScore(communication)
                .lifeAlignmentScore(lifeAlignment)
                .strengths(strengths)
                .friction(friction)
                .suggestedAction(action)
                .summary(summary)
                .build();
    }
}
