package com.astroloop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AstrologerResponse {
    private Long id;
    private String name;
    private String avatarUrl;
    private String bio;
    private boolean verified;
    private String expertise;
    private List<String> languages;
    private int experienceYears;
    private BigDecimal rating;
    private BigDecimal pricePerSession;
    private String availability;
    private int totalSessions;
    private List<String> recommendedReasons;
    private int recommendationScore;
}
