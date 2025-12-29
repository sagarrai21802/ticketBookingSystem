package com.irctc.booking.model;

/**
 * Gender enum for passengers
 */
public enum Gender {
    MALE("M", "Male"),
    FEMALE("F", "Female"),
    TRANSGENDER("T", "Transgender");

    private final String code;
    private final String displayName;

    Gender(String code, String displayName) {
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
