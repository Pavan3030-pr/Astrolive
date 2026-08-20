package com.astroloop.service;

import com.astroloop.config.JwtUtil;
import com.astroloop.dto.AuthResponse;
import com.astroloop.dto.LoginRequest;
import com.astroloop.dto.RegisterRequest;
import com.astroloop.entity.User;
import com.astroloop.enums.PremiumTier;
import com.astroloop.repository.AstrologyProfileRepository;
import com.astroloop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AstrologyProfileRepository profileRepository;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .referralCode("ASTRO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .active(true)
                .premium(false)
                .tier(PremiumTier.BASIC)
                .build();

        // Handle referral
        if (request.getReferralCode() != null && !request.getReferralCode().isEmpty()) {
            User referrer = userRepository.findByReferralCode(request.getReferralCode()).orElse(null);
            if (referrer != null) {
                user.setReferredBy(referrer);
            }
        }

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        boolean hasProfile = profileRepository.findByUserId(user.getId()).isPresent();

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .hasProfile(hasProfile)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        boolean hasProfile = profileRepository.findByUserId(user.getId()).isPresent();

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .hasProfile(hasProfile)
                .build();
    }
}
