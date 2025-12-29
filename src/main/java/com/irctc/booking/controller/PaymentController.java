package com.irctc.booking.controller;

import com.irctc.booking.dto.request.PaymentRequest;
import com.irctc.booking.dto.response.ApiResponse;
import com.irctc.booking.model.Payment;
import com.irctc.booking.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for payment processing
 */
@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payments", description = "Payment processing APIs")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    @Operation(summary = "Process payment", description = "Process payment for a booking")
    public ResponseEntity<ApiResponse<Payment>> processPayment(
            @Valid @RequestBody PaymentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Payment payment = paymentService.processPayment(request, userDetails.getUsername());
        
        if (payment.getStatus().name().equals("SUCCESS")) {
            return ResponseEntity.ok(ApiResponse.success("Payment successful! Booking confirmed.", payment));
        } else {
            return ResponseEntity.ok(ApiResponse.error("Payment failed: " + payment.getErrorMessage()));
        }
    }

    @GetMapping("/transaction/{transactionId}")
    @Operation(summary = "Get payment by transaction ID")
    public ResponseEntity<ApiResponse<Payment>> getPaymentByTransactionId(@PathVariable String transactionId) {
        Payment payment = paymentService.getPaymentByTransactionId(transactionId);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    @GetMapping("/booking/{pnr}")
    @Operation(summary = "Get payment for booking")
    public ResponseEntity<ApiResponse<Payment>> getPaymentByPnr(@PathVariable String pnr) {
        Payment payment = paymentService.getPaymentByPnr(pnr);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    @PostMapping("/refund/{pnr}")
    @Operation(summary = "Process refund", description = "Process refund for cancelled booking")
    public ResponseEntity<ApiResponse<Payment>> processRefund(
            @PathVariable String pnr,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Payment payment = paymentService.processRefund(pnr, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Refund processed successfully", payment));
    }
}
