package com.irctc.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Passenger Entity - Represents individual passengers in a booking
 */
@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Passenger name is required")
    private String name;

    @Column(nullable = false)
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 125, message = "Age cannot exceed 125")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BerthPreference berthPreference = BerthPreference.NO_PREFERENCE;

    // Ticket details after chart preparation
    private String coachNumber;   // e.g., "B1"
    private Integer seatNumber;   // e.g., 45
    
    @Enumerated(EnumType.STRING)
    private BerthType allocatedBerth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PassengerStatus status = PassengerStatus.CONFIRMED;

    // Waitlist position if applicable
    private Integer waitlistNumber;

    // RAC position if applicable
    private Integer racNumber;

    // Food preference
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private FoodPreference foodPreference = FoodPreference.NO_FOOD;

    // ID proof (optional)
    @Enumerated(EnumType.STRING)
    private IdProofType idProofType;

    private String idProofNumber;

    // Concession category
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ConcessionCategory concessionCategory = ConcessionCategory.NONE;

    // Nationality
    @Column(length = 50)
    @Builder.Default
    private String nationality = "Indian";
}
