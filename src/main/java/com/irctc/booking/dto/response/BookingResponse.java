package com.irctc.booking.dto.response;

import com.irctc.booking.model.BookingStatus;
import com.irctc.booking.model.CoachClass;
import com.irctc.booking.model.QuotaType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for booking details
 */
@Data
@Builder
public class BookingResponse {
    // PNR Details
    private String pnrNumber;
    private BookingStatus status;
    
    // Train Details
    private String trainNumber;
    private String trainName;
    private String trainType;
    
    // Journey Details
    private String sourceStation;
    private String sourceStationName;
    private String destinationStation;
    private String destinationStationName;
    private LocalDate journeyDate;
    private String departureTime;
    private String arrivalTime;
    
    // Class and Quota
    private CoachClass coachClass;
    private QuotaType quotaType;
    
    // Passengers
    private List<PassengerResponse> passengers;
    
    // Fare Details
    private BigDecimal baseFare;
    private BigDecimal reservationCharge;
    private BigDecimal serviceTax;
    private BigDecimal cateringCharge;
    private BigDecimal totalFare;
    private Integer distanceKm;
    
    // Contact Details
    private String contactEmail;
    private String contactMobile;
    
    // Timestamps
    private LocalDateTime bookedAt;
    private LocalDateTime cancelledAt;
    
    // Refund if cancelled
    private BigDecimal refundAmount;
    
    // Transaction ID
    private String transactionId;
}
