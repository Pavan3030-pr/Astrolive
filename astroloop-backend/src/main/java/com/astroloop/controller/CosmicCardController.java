package com.astroloop.controller;

import com.astroloop.dto.CosmicCardResponse;
import com.astroloop.service.CosmicCardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cosmic-card")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CosmicCardController {

    private final CosmicCardService cosmicCardService;

    @PostMapping("/generate")
    public ResponseEntity<CosmicCardResponse> generateCard(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        CosmicCardResponse response = cosmicCardService.generateCard(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-cards")
    public ResponseEntity<List<CosmicCardResponse>> getMyCards(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        List<CosmicCardResponse> response = cosmicCardService.getUserCards(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/share/{shareId}")
    public ResponseEntity<CosmicCardResponse> getSharedCard(
            @PathVariable String shareId,
            HttpServletRequest httpRequest) {
        String viewerIp = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");
        String referrerUrl = httpRequest.getHeader("Referer");
        CosmicCardResponse response = cosmicCardService.getCardByShareId(
                shareId, viewerIp, userAgent, referrerUrl);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/share/{shareId}")
    public ResponseEntity<CosmicCardResponse> incrementShare(@PathVariable String shareId) {
        CosmicCardResponse response = cosmicCardService.incrementShare(shareId);
        return ResponseEntity.ok(response);
    }
}
