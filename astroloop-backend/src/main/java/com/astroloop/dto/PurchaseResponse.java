package com.astroloop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PurchaseResponse {
    private Long id;
    private String productName;
    private BigDecimal amount;
    private String status;
    private boolean simulated;
    private LocalDateTime createdAt;
}
