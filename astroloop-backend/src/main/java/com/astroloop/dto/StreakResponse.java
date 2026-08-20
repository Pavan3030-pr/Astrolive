package com.astroloop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class StreakResponse {
    private int currentStreak;
    private int longestStreak;
    private int totalCheckIns;
    private LocalDate lastCheckInDate;
    private boolean checkedInToday;
}
