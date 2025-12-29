package com.irctc.booking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Response DTO for train search results
 */
@Data
@Builder
public class TrainSearchResponse {
    private String trainNumber;
    private String trainName;
    private String trainType;
    
    // Source station details
    private String sourceStation;
    private String sourceStationName;
    private LocalTime departureTime;
    
    // Destination station details  
    private String destinationStation;
    private String destinationStationName;
    private LocalTime arrivalTime;
    
    // Journey duration
    private String duration;
    private Integer distanceKm;
    
    // Running days (1=Sun, 2=Mon, etc.)
    private List<Integer> runningDays;
    private String runningDaysText;
    
    // Seat availability by class
    private Map<String, SeatAvailability> seatAvailability;
    
    // Has pantry car
    private Boolean hasPantry;
    
    @Data
    @Builder
    public static class SeatAvailability {
        private String coachClass;
        private String className;
        private Integer availableSeats;
        private BigDecimal fare;
        private String status; // "AVAILABLE", "RAC", "WAITING LIST", "NOT AVAILABLE"
        private Integer waitlistCount;
    }
}
