package com.astroloop.controller;

import com.astroloop.dto.StreakResponse;
import com.astroloop.service.StreakService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/streak")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StreakController {

    private final StreakService streakService;

    @PostMapping("/check-in")
    public ResponseEntity<StreakResponse> checkIn(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        StreakResponse response = streakService.checkIn(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<StreakResponse> getStreak(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        StreakResponse response = streakService.getStreak(userId);
        return ResponseEntity.ok(response);
    }
}
