package com.astroloop.service;

import com.astroloop.dto.CosmicCardResponse;
import com.astroloop.entity.AstrologyProfile;
import com.astroloop.entity.CardView;
import com.astroloop.entity.CosmicCard;
import com.astroloop.entity.User;
import com.astroloop.enums.CosmicElement;
import com.astroloop.repository.*;
import com.astroloop.util.CosmicEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CosmicCardService {

    private final CosmicCardRepository cosmicCardRepository;
    private final CardViewRepository cardViewRepository;
    private final UserRepository userRepository;
    private final AstrologyProfileRepository profileRepository;

    public CosmicCardResponse generateCard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        AstrologyProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Please create your profile first"));

        LocalDate today = LocalDate.now();

        // Check if user already has a card today
        List<CosmicCard> todayCards = cosmicCardRepository.findByUserIdOrderByCreatedAtDesc(userId);
        if (!todayCards.isEmpty()) {
            CosmicCard lastCard = todayCards.get(0);
            if (lastCard.getCreatedAt() != null && lastCard.getCreatedAt().toLocalDate().equals(today)) {
                return mapToResponse(lastCard, user);
            }
        }

        CosmicElement element = CosmicEngine.getLuckyElement(today, userId);
        int energy = CosmicEngine.getEnergyScore(today, userId);

        String cosmicMessage = CosmicEngine.getCosmicMessage(today, userId);
        String careerInsight = CosmicEngine.getCareerInsight(today, userId);
        String loveInsight = CosmicEngine.getLoveInsight(today, userId);
        String moneyInsight = CosmicEngine.getMoneyInsight(today, userId);

        String shareId = UUID.randomUUID().toString().substring(0, 12);

        CosmicCard card = CosmicCard.builder()
                .user(user)
                .shareId(shareId)
                .cosmicMessage(cosmicMessage)
                .careerInsight(careerInsight)
                .loveInsight(loveInsight)
                .moneyInsight(moneyInsight)
                .energyScore(energy)
                .luckyElement(element)
                .zodiacSign(profile.getZodiacSign())
                .viewCount(0)
                .shareCount(0)
                .build();

        card = cosmicCardRepository.save(card);
        return mapToResponse(card, user);
    }

    public CosmicCardResponse getCardByShareId(String shareId, String viewerIp, String userAgent, String referrerUrl) {
        CosmicCard card = cosmicCardRepository.findByShareId(shareId)
                .orElseThrow(() -> new IllegalArgumentException("Cosmic Card not found"));

        card.setViewCount(card.getViewCount() + 1);
        cosmicCardRepository.save(card);

        CardView cardView = CardView.builder()
                .card(card)
                .viewerIp(viewerIp)
                .userAgent(userAgent)
                .referrerUrl(referrerUrl)
                .build();
        cardViewRepository.save(cardView);

        User user = card.getUser();
        return mapToResponse(card, user);
    }

    public CosmicCardResponse getCardByShareIdPublic(String shareId) {
        CosmicCard card = cosmicCardRepository.findByShareId(shareId)
                .orElseThrow(() -> new IllegalArgumentException("Cosmic Card not found"));
        return mapToResponse(card, card.getUser());
    }

    public List<CosmicCardResponse> getUserCards(Long userId) {
        List<CosmicCard> cards = cosmicCardRepository.findByUserIdOrderByCreatedAtDesc(userId);
        User user = userRepository.findById(userId).orElse(null);
        return cards.stream()
                .map(card -> mapToResponse(card, user))
                .collect(Collectors.toList());
    }

    public CosmicCardResponse incrementShare(String shareId) {
        CosmicCard card = cosmicCardRepository.findByShareId(shareId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        card.setShareCount(card.getShareCount() + 1);
        card = cosmicCardRepository.save(card);
        return mapToResponse(card, card.getUser());
    }

    private CosmicCardResponse mapToResponse(CosmicCard card, User user) {
        String shareUrl = "http://localhost:5173/cosmic-card/" + card.getShareId();
        return CosmicCardResponse.builder()
                .id(card.getId())
                .userName(user.getFirstName())
                .date(card.getCreatedAt() != null ?
                    card.getCreatedAt().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")) :
                    LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")))
                .cosmicMessage(card.getCosmicMessage())
                .careerInsight(card.getCareerInsight())
                .loveInsight(card.getLoveInsight())
                .moneyInsight(card.getMoneyInsight())
                .energyScore(card.getEnergyScore())
                .luckyElement(card.getLuckyElement())
                .zodiacSign(card.getZodiacSign())
                .shareId(card.getShareId())
                .shareUrl(shareUrl)
                .viewCount(card.getViewCount())
                .shareCount(card.getShareCount())
                .createdAt(card.getCreatedAt())
                .build();
    }
}
