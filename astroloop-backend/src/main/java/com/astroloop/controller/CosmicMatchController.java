package com.astroloop.controller;

import com.astroloop.dto.CosmicMatchRequest;
import com.astroloop.dto.CosmicMatchResponse;
import com.astroloop.service.CosmicMatchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cosmic-match")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CosmicMatchController {

    private final CosmicMatchService cosmicMatchService;

    @PostMapping
    public ResponseEntity<CosmicMatchResponse> calculateMatch(
            @Valid @RequestBody CosmicMatchRequest request,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        CosmicMatchResponse response = cosmicMatchService.calculateMatch(userId, request);
        return ResponseEntity.ok(response);
    }
}
