package com.irctc.booking.model;

/**
 * Payment transaction status
 */
public enum PaymentStatus {
    PENDING("Pending", "Payment initiated but not completed"),
    SUCCESS("Success", "Payment successful"),
    FAILED("Failed", "Payment failed"),
    REFUND_INITIATED("Refund Initiated", "Refund process started"),
    REFUNDED("Refunded", "Refund completed"),
    PARTIAL_REFUND("Partial Refund", "Partial refund processed"),
    CANCELLED("Cancelled", "Payment cancelled by user");

    private final String displayName;
    private final String description;

    PaymentStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
