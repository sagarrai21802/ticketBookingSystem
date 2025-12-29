package com.irctc.booking.dto.request;

import com.irctc.booking.model.*;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Request DTO for passenger details in a booking
 */
@Data
public class PassengerRequest {
    
    @NotBlank(message = "Passenger name is required")
    private String name;
    
    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 125, message = "Age cannot exceed 125")
    private Integer age;
    
    @NotNull(message = "Gender is required")
    private Gender gender;
    
    private BerthPreference berthPreference = BerthPreference.NO_PREFERENCE;
    
    private FoodPreference foodPreference = FoodPreference.NO_FOOD;
    
    private IdProofType idProofType;
    
    private String idProofNumber;
    
    private ConcessionCategory concessionCategory = ConcessionCategory.NONE;
    
    private String nationality = "Indian";
}
