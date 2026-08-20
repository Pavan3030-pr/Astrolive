package com.astroloop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CosmicMatchRequest {
    @NotNull(message = "Partner date of birth is required")
    private LocalDate partnerDateOfBirth;

    private String partnerName;
}
