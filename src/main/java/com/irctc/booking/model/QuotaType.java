package com.irctc.booking.model;

/**
 * Quota types for Indian Railway booking
 */
public enum QuotaType {
    GENERAL("GN", "General Quota", "Regular quota for all passengers"),
    LADIES("LD", "Ladies Quota", "Reserved for female passengers"),
    TATKAL("TQ", "Tatkal Quota", "Emergency booking one day before journey"),
    PREMIUM_TATKAL("PT", "Premium Tatkal", "Dynamic pricing tatkal"),
    LOWER_BERTH("LB", "Lower Berth", "For senior citizens and disabled"),
    DEFENCE("DF", "Defence Quota", "For defence personnel"),
    FOREIGN_TOURIST("FT", "Foreign Tourist", "For foreign nationals"),
    PARLIAMENT_HOUSE("PH", "Parliament House", "For MPs"),
    DUTY_PASS("DP", "Duty Pass", "For railway employees"),
    DIVYANGJAN("HP", "Divyangjan Quota", "For persons with disabilities");

    private final String code;
    private final String displayName;
    private final String description;

    QuotaType(String code, String displayName, String description) {
        this.code = code;
        this.displayName = displayName;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
