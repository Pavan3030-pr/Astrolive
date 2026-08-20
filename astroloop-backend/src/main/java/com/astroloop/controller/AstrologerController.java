package com.astroloop.controller;

import com.astroloop.dto.AstrologerResponse;
import com.astroloop.service.AstrologerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/astrologers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AstrologerController {

    private final AstrologerService astrologerService;

    @GetMapping
    public ResponseEntity<List<AstrologerResponse>> searchAstrologers(
            @RequestParam(required = false) String expertise,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) BigDecimal maxPrice) {
        List<AstrologerResponse> response = astrologerService.searchAstrologers(
                expertise, language, minRating, maxPrice);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recommended")
    public ResponseEntity<List<AstrologerResponse>> getRecommended(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        List<AstrologerResponse> response = astrologerService.getRecommended(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AstrologerResponse> getAstrologer(@PathVariable Long id) {
        AstrologerResponse response = astrologerService.getAstrologer(id);
        return ResponseEntity.ok(response);
    }
}
