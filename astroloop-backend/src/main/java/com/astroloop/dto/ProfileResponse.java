package com.astroloop.dto;

import com.astroloop.enums.Interest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private LocalTime timeOfBirth;
    private String placeOfBirth;
    private Interest primaryInterest;
    private String zodiacSign;
    private String moonSign;
    private String risingSign;
}
