package com.irctc.booking.dto.request;

import com.irctc.booking.model.CoachClass;
import com.irctc.booking.model.QuotaType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Request DTO for creating a booking
 */
@Data
public class BookingRequest {
    
    @NotBlank(message = "Train number is required")
    private String trainNumber;
    
    @NotBlank(message = "Source station code is required")
    private String sourceStationCode;
    
    @NotBlank(message = "Destination station code is required")
    private String destinationStationCode;
    
    @NotNull(message = "Journey date is required")
    @Future(message = "Journey date must be in the future")
    private LocalDate journeyDate;
    
    @NotNull(message = "Coach class is required")
    private CoachClass coachClass;
    
    private QuotaType quotaType = QuotaType.GENERAL;
    
    @NotEmpty(message = "At least one passenger is required")
    @Size(max = 6, message = "Maximum 6 passengers allowed per booking")
    @Valid
    private List<PassengerRequest> passengers;
    
    @Email(message = "Invalid email format")
    private String contactEmail;
    
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
    private String contactMobile;
}
