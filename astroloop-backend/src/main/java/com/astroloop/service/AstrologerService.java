package com.astroloop.service;

import com.astroloop.dto.AstrologerResponse;
import com.astroloop.entity.Astrologer;
import com.astroloop.entity.AstrologyProfile;
import com.astroloop.repository.AstrologerRepository;
import com.astroloop.repository.AstrologyProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AstrologerService {

    private final AstrologerRepository astrologerRepository;
    private final AstrologyProfileRepository profileRepository;

    public List<AstrologerResponse> searchAstrologers(
            String expertise, String language, BigDecimal minRating, BigDecimal maxPrice) {
        List<Astrologer> astrologers = astrologerRepository.search(expertise, language, minRating, maxPrice);
        return astrologers.stream()
                .map(a -> mapToResponse(a, null))
                .collect(Collectors.toList());
    }

    public List<AstrologerResponse> getRecommended(Long userId) {
        AstrologyProfile profile = profileRepository.findByUserId(userId).orElse(null);
        List<Astrologer> allAstrologers = astrologerRepository.findByActiveTrue();

        List<AstrologerResponse> responses = allAstrologers.stream()
                .map(a -> mapToResponse(a, profile))
                .sorted(Comparator.comparingInt(AstrologerResponse::getRecommendationScore).reversed())
                .collect(Collectors.toList());

        return responses;
    }

    public AstrologerResponse getAstrologer(Long id) {
        Astrologer astrologer = astrologerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Astrologer not found"));
        return mapToResponse(astrologer, null);
    }

    private AstrologerResponse mapToResponse(Astrologer astrologer, AstrologyProfile profile) {
        List<String> languages = Arrays.asList(astrologer.getLanguages().split(",\\s*"));
        List<String> reasons = new ArrayList<>();
        int score = 50;

        if (profile != null) {
            String interest = profile.getPrimaryInterest().name().toLowerCase();
            if (astrologer.getExpertise().toLowerCase().contains(interest)) {
                reasons.add("Matches your " + profile.getPrimaryInterest().name().toLowerCase() + " interest");
                score += 20;
            }

            for (String lang : languages) {
                if (astrologer.getLanguages().toLowerCase().contains(lang.trim().toLowerCase())) {
                    reasons.add("Speaks your preferred language: " + lang.trim());
                    score += 10;
                    break;
                }
            }

            if (astrologer.getRating().compareTo(new BigDecimal("4.7")) >= 0) {
                reasons.add("Top-rated astrologer with " + astrologer.getRating() + " rating");
                score += 15;
            }

            if (astrologer.getExperienceYears() >= 10) {
                reasons.add(astrologer.getExperienceYears() + " years of proven experience");
                score += 10;
            }

            if (reasons.isEmpty()) {
                reasons.add("Highly rated and experienced astrologer");
            }
        } else {
            if (astrologer.getRating().compareTo(new BigDecimal("4.7")) >= 0) {
                reasons.add("Top-rated with " + astrologer.getRating() + " stars");
                score += 10;
            }
            if (astrologer.isVerified()) {
                reasons.add("Verified professional astrologer");
                score += 5;
            }
        }

        return AstrologerResponse.builder()
                .id(astrologer.getId())
                .name(astrologer.getName())
                .avatarUrl(astrologer.getAvatarUrl())
                .bio(astrologer.getBio())
                .verified(astrologer.isVerified())
                .expertise(astrologer.getExpertise())
                .languages(languages)
                .experienceYears(astrologer.getExperienceYears())
                .rating(astrologer.getRating())
                .pricePerSession(astrologer.getPricePerSession())
                .availability(astrologer.getAvailability())
                .totalSessions(astrologer.getTotalSessions())
                .recommendedReasons(reasons)
                .recommendationScore(score)
                .build();
    }
}
