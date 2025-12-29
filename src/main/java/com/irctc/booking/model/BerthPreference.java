package com.irctc.booking.model;

/**
 * Berth preference for booking
 */
public enum BerthPreference {
    LOWER("LB", "Lower Berth"),
    MIDDLE("MB", "Middle Berth"),
    UPPER("UB", "Upper Berth"),
    SIDE_LOWER("SL", "Side Lower"),
    SIDE_UPPER("SU", "Side Upper"),
    WINDOW("WS", "Window Seat"),
    AISLE("AS", "Aisle Seat"),
    NO_PREFERENCE("NP", "No Preference");

    private final String code;
    private final String displayName;

    BerthPreference(String code, String displayName) {
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
