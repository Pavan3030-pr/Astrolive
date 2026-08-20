package com.astroloop.dto;

import com.astroloop.enums.ConsultationStatus;
import com.astroloop.enums.ConsultationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ConsultationResponse {
    private Long id;
    private Long astrologerId;
    private String astrologerName;
    private String concern;
    private ConsultationType consultationType;
    private BigDecimal amount;
    private ConsultationStatus status;
    private LocalDateTime scheduledTime;
    private boolean paymentSimulated;
    private LocalDateTime createdAt;
}
