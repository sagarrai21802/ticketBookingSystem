package com.irctc.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Booking Entity - Represents a ticket booking
 * Contains PNR, train details, passengers, and payment info
 */
@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String pnrNumber;  // 10-digit PNR like IRCTC

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_station_id", nullable = false)
    private Station sourceStation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_station_id", nullable = false)
    private Station destinationStation;

    @Column(nullable = false)
    private LocalDate journeyDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoachClass coachClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private BookingStatus status = BookingStatus.PENDING;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers;

    // Fare details
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal baseFare;

    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal reservationCharge = BigDecimal.valueOf(40);

    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal serviceTax = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal cateringCharge = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totalFare;

    // Distance traveled (for fare calculation)
    private Integer distanceKm;

    // Quota type
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private QuotaType quotaType = QuotaType.GENERAL;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime bookedAt = LocalDateTime.now();

    private LocalDateTime cancelledAt;

    @Column(precision = 10, scale = 2)
    private BigDecimal refundAmount;

    // Contact information for booking
    @Email
    private String contactEmail;

    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String contactMobile;

    // Transaction ID from payment
    private String transactionId;

    // Method to add passengers
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        passenger.setBooking(this);
    }
}
