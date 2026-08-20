package com.astroloop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "card_views")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CosmicCard card;

    private String viewerIp;

    private String userAgent;

    private String referrerUrl;

    private boolean convertedToSignup = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "converted_user_id")
    private User convertedUser;

    @CreationTimestamp
    private LocalDateTime viewedAt;
}
