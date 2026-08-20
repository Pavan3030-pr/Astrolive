package com.astroloop.dto;

import com.astroloop.enums.ConsultationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultationRequest {
    @NotNull(message = "Astrologer ID is required")
    private Long astrologerId;

    @NotBlank(message = "Concern is required")
    private String concern;

    @NotNull(message = "Consultation type is required")
    private ConsultationType consultationType;

    @NotNull(message = "Scheduled time is required")
    private LocalDateTime scheduledTime;
}
