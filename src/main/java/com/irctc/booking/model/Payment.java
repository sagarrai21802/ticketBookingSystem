package com.irctc.booking.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Payment Entity - Represents payment transactions
 */
@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;  // Unique transaction ID

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    // Payment gateway reference
    private String gatewayTransactionId;
    private String gatewayOrderId;

    // Bank/Card details (masked)
    private String paymentBank;
    private String maskedCardNumber;  // e.g., "**** **** **** 1234"

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime initiatedAt = LocalDateTime.now();

    private LocalDateTime completedAt;

    // Refund details
    private Boolean isRefunded;
    private BigDecimal refundAmount;
    private LocalDateTime refundedAt;
    private String refundTransactionId;

    // Error details if failed
    private String errorCode;
    private String errorMessage;

    // User who made the payment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
