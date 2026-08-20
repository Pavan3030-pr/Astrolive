package com.astroloop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CosmicMatchResponse {
    private String user1Name;
    private String user2Name;
    private String user1Sign;
    private String user2Sign;
    private int overallScore;
    private int loveScore;
    private int communicationScore;
    private int lifeAlignmentScore;
    private List<String> strengths;
    private List<String> friction;
    private String suggestedAction;
    private String summary;
}
