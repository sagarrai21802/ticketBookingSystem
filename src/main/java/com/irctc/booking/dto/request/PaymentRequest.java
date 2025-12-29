package com.irctc.booking.dto.request;

import com.irctc.booking.model.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Request DTO for processing payment
 */
@Data
public class PaymentRequest {
    
    @NotBlank(message = "PNR number is required")
    private String pnrNumber;
    
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
    
    // For card payments
    private String cardNumber;
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;
    
    // For UPI payments
    private String upiId;
    
    // For net banking
    private String bankCode;
}
