package com.astroloop.controller;

import com.astroloop.dto.CosmicCardResponse;
import com.astroloop.service.CosmicCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PublicController {

    private final CosmicCardService cosmicCardService;

    @GetMapping("/cosmic-card/{shareId}")
    public ResponseEntity<CosmicCardResponse> getPublicCard(@PathVariable String shareId) {
        CosmicCardResponse response = cosmicCardService.getCardByShareIdPublic(shareId);
        return ResponseEntity.ok(response);
    }
}
