package com.astroloop.service;

import com.astroloop.dto.ConsultationRequest;
import com.astroloop.dto.ConsultationResponse;
import com.astroloop.entity.Astrologer;
import com.astroloop.entity.Consultation;
import com.astroloop.entity.User;
import com.astroloop.enums.ConsultationStatus;
import com.astroloop.repository.AstrologerRepository;
import com.astroloop.repository.ConsultationRepository;
import com.astroloop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AstrologerRepository astrologerRepository;
    private final UserRepository userRepository;

    public ConsultationResponse bookConsultation(Long userId, ConsultationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Astrologer astrologer = astrologerRepository.findById(request.getAstrologerId())
                .orElseThrow(() -> new IllegalArgumentException("Astrologer not found"));

        // Calculate price based on type and astrologer rate
        BigDecimal amount = astrologer.getPricePerSession();
        if (request.getConsultationType().getDurationMinutes() > 15) {
            amount = amount.multiply(new BigDecimal("1.5"));
        }
        if (request.getConsultationType().getDurationMinutes() > 30) {
            amount = amount.multiply(new BigDecimal("2.0"));
        }

        Consultation consultation = Consultation.builder()
                .user(user)
                .astrologer(astrologer)
                .concern(request.getConcern())
                .consultationType(request.getConsultationType())
                .amount(amount)
                .status(ConsultationStatus.CONFIRMED)
                .scheduledTime(request.getScheduledTime())
                .paymentSimulated(true)
                .build();

        consultation = consultationRepository.save(consultation);

        return mapToResponse(consultation);
    }

    public List<ConsultationResponse> getUserConsultations(Long userId) {
        List<Consultation> consultations = consultationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return consultations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ConsultationResponse mapToResponse(Consultation consultation) {
        return ConsultationResponse.builder()
                .id(consultation.getId())
                .astrologerId(consultation.getAstrologer().getId())
                .astrologerName(consultation.getAstrologer().getName())
                .concern(consultation.getConcern())
                .consultationType(consultation.getConsultationType())
                .amount(consultation.getAmount())
                .status(consultation.getStatus())
                .scheduledTime(consultation.getScheduledTime())
                .paymentSimulated(consultation.isPaymentSimulated())
                .createdAt(consultation.getCreatedAt())
                .build();
    }
}
