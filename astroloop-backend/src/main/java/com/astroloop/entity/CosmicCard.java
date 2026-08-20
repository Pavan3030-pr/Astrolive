package com.astroloop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cosmic_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CosmicCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String shareId;

    @Column(nullable = false)
    private String cosmicMessage;

    @Column(length = 500)
    private String careerInsight;

    @Column(length = 500)
    private String loveInsight;

    @Column(length = 500)
    private String moneyInsight;

    @Column(nullable = false)
    private Integer energyScore;

    @Enumerated(EnumType.STRING)
    private com.astroloop.enums.CosmicElement luckyElement;

    private String zodiacSign;

    @Column(nullable = false)
    private Integer viewCount = 0;

    @Column(nullable = false)
    private Integer shareCount = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
