package com.irctc.booking.model;

/**
 * Passenger ticket status
 */
public enum PassengerStatus {
    CONFIRMED("CNF", "Confirmed"),
    WAITLISTED("WL", "Waitlisted"),
    RAC("RAC", "Reservation Against Cancellation"),
    CANCELLED("CAN", "Cancelled"),
    CAN_MOD("CANMOD", "Cancelled and Modified");

    private final String code;
    private final String displayName;

    PassengerStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
