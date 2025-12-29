package com.irctc.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Station Entity - Represents railway stations across India
 * Uses standard Indian Railway station codes
 */
@Entity
@Table(name = "stations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    @NotBlank(message = "Station code is required")
    private String stationCode;  // e.g., "NDLS" for New Delhi

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Station name is required")
    private String stationName;  // e.g., "New Delhi Railway Station"

    @Column(nullable = false, length = 100)
    @NotBlank(message = "City is required")
    private String city;         // e.g., "New Delhi"

    @Column(nullable = false, length = 50)
    @NotBlank(message = "State is required")
    private String state;        // e.g., "Delhi"

    @Column(length = 50)
    private String zone;         // Railway zone, e.g., "Northern Railway"

    @Column(length = 20)
    private String division;     // Railway division

    private Double latitude;
    private Double longitude;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    // Indicates if station has special facilities
    @Builder.Default
    private Boolean hasWaitingRoom = true;
    
    @Builder.Default
    private Boolean hasParkingFacility = true;
    
    @Builder.Default
    private Boolean hasRestaurant = true;
}
