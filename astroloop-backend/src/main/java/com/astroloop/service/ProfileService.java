package com.astroloop.service;

import com.astroloop.dto.ProfileRequest;
import com.astroloop.dto.ProfileResponse;
import com.astroloop.entity.AstrologyProfile;
import com.astroloop.entity.User;
import com.astroloop.repository.AstrologyProfileRepository;
import com.astroloop.repository.UserRepository;
import com.astroloop.util.CosmicEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final AstrologyProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileResponse createProfile(Long userId, ProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        AstrologyProfile existing = profileRepository.findByUserId(userId).orElse(null);
        if (existing != null) {
            throw new IllegalStateException("Profile already exists. Use update instead.");
        }

        String zodiacSign = CosmicEngine.getZodiacSign(request.getDateOfBirth());

        AstrologyProfile profile = AstrologyProfile.builder()
                .user(user)
                .name(request.getName())
                .dateOfBirth(request.getDateOfBirth())
                .timeOfBirth(request.getTimeOfBirth())
                .placeOfBirth(request.getPlaceOfBirth())
                .primaryInterest(request.getPrimaryInterest())
                .zodiacSign(zodiacSign)
                .moonSign("Moon in " + zodiacSign)
                .risingSign("Rising " + CosmicEngine.getZodiacSign(
                    request.getDateOfBirth().plusDays(90)))
                .build();

        profile = profileRepository.save(profile);
        return mapToResponse(profile);
    }

    public ProfileResponse getProfile(Long userId) {
        AstrologyProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        return mapToResponse(profile);
    }

    public ProfileResponse updateProfile(Long userId, ProfileRequest request) {
        AstrologyProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        profile.setName(request.getName());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setTimeOfBirth(request.getTimeOfBirth());
        profile.setPlaceOfBirth(request.getPlaceOfBirth());
        profile.setPrimaryInterest(request.getPrimaryInterest());
        profile.setZodiacSign(CosmicEngine.getZodiacSign(request.getDateOfBirth()));

        profile = profileRepository.save(profile);
        return mapToResponse(profile);
    }

    private ProfileResponse mapToResponse(AstrologyProfile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getName())
                .dateOfBirth(profile.getDateOfBirth())
                .timeOfBirth(profile.getTimeOfBirth())
                .placeOfBirth(profile.getPlaceOfBirth())
                .primaryInterest(profile.getPrimaryInterest())
                .zodiacSign(profile.getZodiacSign())
                .moonSign(profile.getMoonSign())
                .risingSign(profile.getRisingSign())
                .build();
    }
}
