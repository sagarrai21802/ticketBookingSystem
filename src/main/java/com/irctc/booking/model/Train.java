package com.irctc.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Train Entity - Represents trains in the Indian Railway system
 */
@Entity
@Table(name = "trains")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    @NotBlank(message = "Train number is required")
    private String trainNumber;  // e.g., "12301"

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Train name is required")
    private String trainName;    // e.g., "Rajdhani Express"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainType trainType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_station_id", nullable = false)
    private Station sourceStation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_station_id", nullable = false)
    private Station destinationStation;

    // Days of operation (Sunday=1, Monday=2, ... Saturday=7)
    @ElementCollection
    @CollectionTable(name = "train_running_days", joinColumns = @JoinColumn(name = "train_id"))
    @Column(name = "day_of_week")
    private List<Integer> runningDays;

    // Seat availability for different classes
    @Column(name = "total_seats1_a", nullable = false)
    @Builder.Default
    private Integer totalSeats1A = 0;     // First AC

    @Column(name = "total_seats2_a", nullable = false)
    @Builder.Default
    private Integer totalSeats2A = 0;     // Second AC

    @Column(name = "total_seats3_a", nullable = false)
    @Builder.Default
    private Integer totalSeats3A = 0;     // Third AC

    @Column(name = "total_seats_sl", nullable = false)
    @Builder.Default
    private Integer totalSeatsSL = 0;     // Sleeper

    @Column(name = "total_seats_cc", nullable = false)
    @Builder.Default
    private Integer totalSeatsCC = 0;     // Chair Car

    @Column(name = "total_seats2_s", nullable = false)
    @Builder.Default
    private Integer totalSeats2S = 0;     // Second Sitting

    // Fare rates per kilometer
    @Column(name = "fare_rate1_a", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal fareRate1A = BigDecimal.valueOf(4.5);

    @Column(name = "fare_rate2_a", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal fareRate2A = BigDecimal.valueOf(2.8);

    @Column(name = "fare_rate3_a", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal fareRate3A = BigDecimal.valueOf(1.8);

    @Column(name = "fare_rate_sl", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal fareRateSL = BigDecimal.valueOf(0.9);

    @Column(name = "fare_rate_cc", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal fareRateCC = BigDecimal.valueOf(1.2);

    @Column(name = "fare_rate2_s", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal fareRate2S = BigDecimal.valueOf(0.6);

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(nullable = false)
    @Builder.Default
    private Boolean hasPantry = false;

    @Column(length = 50)
    private String coachType;  // LHB, ICF, etc.
}
