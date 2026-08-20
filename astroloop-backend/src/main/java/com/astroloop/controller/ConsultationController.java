package com.astroloop.controller;

import com.astroloop.dto.ConsultationRequest;
import com.astroloop.dto.ConsultationResponse;
import com.astroloop.service.ConsultationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<ConsultationResponse> bookConsultation(
            @Valid @RequestBody ConsultationRequest request,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        ConsultationResponse response = consultationService.bookConsultation(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ConsultationResponse>> getUserConsultations(
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("currentUserId");
        List<ConsultationResponse> response = consultationService.getUserConsultations(userId);
        return ResponseEntity.ok(response);
    }
}
