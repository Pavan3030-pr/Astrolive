package com.astroloop.controller;

import com.astroloop.dto.ChallengeResponse;
import com.astroloop.service.ChallengeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> getChallenges(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        return ResponseEntity.ok(challengeService.getChallenges(userId));
    }

    @PostMapping("/{challengeId}/join")
    public ResponseEntity<ChallengeResponse> joinChallenge(
            @PathVariable Long challengeId,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        return ResponseEntity.ok(challengeService.joinChallenge(userId, challengeId));
    }

    @PostMapping("/{challengeId}/complete")
    public ResponseEntity<ChallengeResponse> completeChallenge(
            @PathVariable Long challengeId,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        return ResponseEntity.ok(challengeService.completeChallenge(userId, challengeId));
    }
}
