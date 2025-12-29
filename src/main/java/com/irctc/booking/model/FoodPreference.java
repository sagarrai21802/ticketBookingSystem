package com.irctc.booking.model;

/**
 * Food preference for catering
 */
public enum FoodPreference {
    VEG("V", "Vegetarian"),
    NON_VEG("N", "Non-Vegetarian"),
    JAIN("J", "Jain (No Onion/Garlic)"),
    NO_FOOD("NF", "No Food Required");

    private final String code;
    private final String displayName;

    FoodPreference(String code, String displayName) {
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
