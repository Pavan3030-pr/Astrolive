package com.astroloop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "astrologers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Astrologer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String avatarUrl;

    private String bio;

    @Column(nullable = false)
    private boolean verified = false;

    @Column(nullable = false)
    private String expertise;

    private String languages;

    @Column(nullable = false)
    private int experienceYears;

    @Column(precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(nullable = false)
    private BigDecimal pricePerSession;

    private String availability;

    private int totalSessions;

    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
