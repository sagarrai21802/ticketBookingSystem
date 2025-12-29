package com.irctc.booking.service;

import com.irctc.booking.dto.request.PaymentRequest;
import com.irctc.booking.exception.PaymentException;
import com.irctc.booking.exception.ResourceNotFoundException;
import com.irctc.booking.model.*;
import com.irctc.booking.repository.*;
import com.irctc.booking.util.PnrGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for payment processing (Mock implementation)
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingService bookingService;

    /**
     * Process payment for a booking
     */
    @Transactional
    public Payment processPayment(PaymentRequest request, String username) {
        // Get booking
        Booking booking = bookingRepository.findByPnrNumber(request.getPnrNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "PNR", request.getPnrNumber()));

        // Verify ownership
        if (!booking.getUser().getUsername().equals(username)) {
            throw new PaymentException("You can only pay for your own bookings");
        }

        // Check booking status
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new PaymentException("Payment can only be made for pending bookings");
        }

        // Check if payment already exists
        if (paymentRepository.findByBookingPnrNumber(request.getPnrNumber()).isPresent()) {
            throw new PaymentException("Payment already exists for this booking");
        }

        // Get user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Generate transaction ID
        String transactionId = PnrGenerator.generateTransactionId();

        // Create payment record
        Payment payment = Payment.builder()
                .transactionId(transactionId)
                .booking(booking)
                .user(user)
                .amount(booking.getTotalFare())
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .build();

        // Mock payment processing
        boolean paymentSuccess = mockPaymentGateway(request);

        if (paymentSuccess) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setCompletedAt(LocalDateTime.now());
            payment.setGatewayTransactionId("GW" + System.currentTimeMillis());
            
            // Mask card number if provided
            if (request.getCardNumber() != null && request.getCardNumber().length() >= 4) {
                payment.setMaskedCardNumber("**** **** **** " + 
                        request.getCardNumber().substring(request.getCardNumber().length() - 4));
            }

            // Confirm booking
            bookingService.confirmBooking(request.getPnrNumber(), transactionId);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setErrorCode("PAYMENT_DECLINED");
            payment.setErrorMessage("Payment was declined by the bank");
        }

        return paymentRepository.save(payment);
    }

    /**
     * Get payment by transaction ID
     */
    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "transactionId", transactionId));
    }

    /**
     * Get payment for a booking
     */
    public Payment getPaymentByPnr(String pnr) {
        return paymentRepository.findByBookingPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "PNR", pnr));
    }

    /**
     * Process refund for cancelled booking
     */
    @Transactional
    public Payment processRefund(String pnr, String username) {
        Booking booking = bookingRepository.findByPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "PNR", pnr));

        // Verify ownership
        if (!booking.getUser().getUsername().equals(username)) {
            throw new PaymentException("You can only request refund for your own bookings");
        }

        // Check if booking is cancelled
        if (booking.getStatus() != BookingStatus.CANCELLED) {
            throw new PaymentException("Refund can only be processed for cancelled bookings");
        }

        Payment payment = paymentRepository.findByBookingPnrNumber(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "PNR", pnr));

        if (payment.getIsRefunded() != null && payment.getIsRefunded()) {
            throw new PaymentException("Refund has already been processed");
        }

        // Process refund
        payment.setIsRefunded(true);
        payment.setRefundAmount(booking.getRefundAmount());
        payment.setRefundedAt(LocalDateTime.now());
        payment.setRefundTransactionId("REF" + System.currentTimeMillis());
        payment.setStatus(PaymentStatus.REFUNDED);

        return paymentRepository.save(payment);
    }

    /**
     * Mock payment gateway (always returns success in this demo)
     */
    private boolean mockPaymentGateway(PaymentRequest request) {
        // Simulate some processing delay
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // For demo purposes, always return success
        // In real implementation, this would call actual payment gateway
        return true;
    }
}
