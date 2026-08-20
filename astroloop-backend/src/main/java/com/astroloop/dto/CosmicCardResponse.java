package com.astroloop.dto;

import com.astroloop.enums.CosmicElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CosmicCardResponse {
    private Long id;
    private String userName;
    private String date;
    private String cosmicMessage;
    private String careerInsight;
    private String loveInsight;
    private String moneyInsight;
    private int energyScore;
    private CosmicElement luckyElement;
    private String zodiacSign;
    private String shareId;
    private String shareUrl;
    private int viewCount;
    private int shareCount;
    private LocalDateTime createdAt;
}
