package com.astroloop.service;

import com.astroloop.entity.CosmicCard;
import com.astroloop.entity.Referral;
import com.astroloop.entity.User;
import com.astroloop.repository.CosmicCardRepository;
import com.astroloop.repository.ReferralRepository;
import com.astroloop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReferralService {

    private final ReferralRepository referralRepository;
    private final UserRepository userRepository;
    private final CosmicCardRepository cosmicCardRepository;

    public void trackReferral(Long referrerUserId, Long cardId, String visitorIp, String source) {
        User referrer = userRepository.findById(referrerUserId).orElse(null);
        CosmicCard card = cosmicCardRepository.findById(cardId).orElse(null);

        if (referrer != null) {
            Referral referral = Referral.builder()
                    .referrer(referrer)
                    .sharedCard(card)
                    .source(source)
                    .converted(false)
                    .registered(false)
                    .build();
            referralRepository.save(referral);
        }
    }

    public void recordConversion(Long referrerId, Long visitorId) {
        User referrer = userRepository.findById(referrerId).orElse(null);
        User visitor = userRepository.findById(visitorId).orElse(null);

        if (referrer != null && visitor != null) {
            Referral referral = Referral.builder()
                    .referrer(referrer)
                    .visitor(visitor)
                    .source("cosmic-card-register")
                    .converted(true)
                    .registered(true)
                    .build();
            referralRepository.save(referral);
        }
    }

    public Map<String, Object> getReferralStats(Long userId) {
        long totalReferrals = referralRepository.countByReferrerId(userId);
        long registered = referralRepository.countRegisteredByReferrerId(userId);
        double conversionRate = totalReferrals > 0 ?
            (double) registered / totalReferrals * 100 : 0;

        return Map.of(
                "totalReferrals", totalReferrals,
                "registeredReferrals", registered,
                "conversionRate", Math.round(conversionRate * 100.0) / 100.0
        );
    }
}
