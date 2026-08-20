package com.astroloop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "challenges")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private String category;

    @Column(nullable = false)
    private int rewardPoints;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean active = true;

    private boolean premium = false;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
