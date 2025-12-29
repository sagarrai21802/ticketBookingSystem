package com.irctc.booking.model;

/**
 * Booking status in the system
 */
public enum BookingStatus {
    PENDING("Pending", "Booking is pending payment"),
    CONFIRMED("Confirmed", "Booking is confirmed - CNF"),
    WAITLISTED("Waitlisted", "Booking is in waitlist - WL"),
    RAC("RAC", "Reservation Against Cancellation"),
    CANCELLED("Cancelled", "Booking has been cancelled"),
    CHART_PREPARED("Chart Prepared", "Final chart has been prepared"),
    COMPLETED("Completed", "Journey completed");

    private final String displayName;
    private final String description;

    BookingStatus(String displayName, String description) {
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
