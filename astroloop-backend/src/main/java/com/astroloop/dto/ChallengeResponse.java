package com.astroloop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ChallengeResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private int rewardPoints;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean joined;
    private boolean completed;
    private Integer score;
}
