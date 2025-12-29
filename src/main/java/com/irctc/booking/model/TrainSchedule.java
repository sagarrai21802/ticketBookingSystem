package com.irctc.booking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * TrainSchedule Entity - Represents a specific train's schedule for routes
 * Links train with intermediate stations and timing
 */
@Entity
@Table(name = "train_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    // Stop sequence number (1 = origin station)
    @Column(nullable = false)
    private Integer stopNumber;

    @Column(nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false)
    private LocalTime departureTime;

    // Distance from origin in kilometers
    @Column(nullable = false)
    private Integer distanceFromOrigin;

    // Halt duration in minutes
    @Column(nullable = false)
    @Builder.Default
    private Integer haltDuration = 2;

    // Day offset (if train arrives next day, value = 1)
    @Column(nullable = false)
    @Builder.Default
    private Integer dayOffset = 0;

    // Platform number if available
    private String platformNumber;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
