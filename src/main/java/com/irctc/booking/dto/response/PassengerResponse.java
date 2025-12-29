package com.irctc.booking.dto.response;

import com.irctc.booking.model.*;
import lombok.Builder;
import lombok.Data;

/**
 * Response DTO for passenger details in a booking
 */
@Data
@Builder
public class PassengerResponse {
    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
    private BerthPreference berthPreference;
    
    // Allocated seat details
    private String coachNumber;
    private Integer seatNumber;
    private BerthType allocatedBerth;
    
    // Status
    private PassengerStatus status;
    private Integer waitlistNumber;
    private Integer racNumber;
    
    // Preferences
    private FoodPreference foodPreference;
    
    // Concession
    private ConcessionCategory concessionCategory;
}
