package com.irctc.booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * Request DTO for searching trains
 */
@Data
public class TrainSearchRequest {
    
    @NotBlank(message = "Source station is required")
    private String sourceStation;
    
    @NotBlank(message = "Destination station is required")
    private String destinationStation;
    
    @NotNull(message = "Journey date is required")
    private LocalDate journeyDate;
    
    private String coachClass;  // Optional filter
    
    private String quotaType;   // Optional filter
}
