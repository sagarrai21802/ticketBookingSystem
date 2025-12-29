package com.irctc.booking.model;

/**
 * Allocated berth types
 */
public enum BerthType {
    LOWER("LB", "Lower Berth"),
    MIDDLE("MB", "Middle Berth"),
    UPPER("UB", "Upper Berth"),
    SIDE_LOWER("SL", "Side Lower"),
    SIDE_UPPER("SU", "Side Upper"),
    WINDOW("WS", "Window Seat"),
    MIDDLE_SEAT("MS", "Middle Seat"),
    AISLE("AS", "Aisle Seat");

    private final String code;
    private final String displayName;

    BerthType(String code, String displayName) {
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
