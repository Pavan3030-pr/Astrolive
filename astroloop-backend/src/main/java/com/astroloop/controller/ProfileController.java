package com.astroloop.controller;

import com.astroloop.dto.ProfileRequest;
import com.astroloop.dto.ProfileResponse;
import com.astroloop.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<ProfileResponse> createProfile(
            @Valid @RequestBody ProfileRequest request,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        ProfileResponse response = profileService.createProfile(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        ProfileResponse response = profileService.getProfile(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(
            @Valid @RequestBody ProfileRequest request,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        ProfileResponse response = profileService.updateProfile(userId, request);
        return ResponseEntity.ok(response);
    }
}
