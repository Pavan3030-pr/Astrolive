package com.astroloop.controller;

import com.astroloop.service.ReferralService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/referrals")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReferralController {

    private final ReferralService referralService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        return ResponseEntity.ok(referralService.getReferralStats(userId));
    }
}
