package com.irctc.booking.model;

/**
 * Concession categories for discounted fares
 */
public enum ConcessionCategory {
    NONE("None", 0),
    SENIOR_CITIZEN_MALE("Senior Citizen (Male 60+)", 40),
    SENIOR_CITIZEN_FEMALE("Senior Citizen (Female 58+)", 50),
    DIVYANGJAN("Divyangjan (Disabled)", 75),
    STUDENT("Student", 25),
    PRESS("Press Correspondent", 50),
    TEACHER("Teacher", 25),
    SCOUT_GUIDE("Scout/Guide", 50),
    KISAN("Kisaan (Farmer)", 50),
    RECIPIENT_GALLANTRY("Gallantry Award Recipient", 75),
    WAR_WIDOW("War Widow", 75),
    PATIENT("Patient (Specific conditions)", 75);

    private final String displayName;
    private final int discountPercent;

    ConcessionCategory(String displayName, int discountPercent) {
        this.displayName = displayName;
        this.discountPercent = discountPercent;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }
}
