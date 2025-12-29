package com.irctc.booking.model;

/**
 * Coach/Class types in Indian Railways
 */
public enum CoachClass {
    FIRST_AC("1A", "First AC", "First Class Air Conditioned - Most premium"),
    SECOND_AC("2A", "Second AC", "Two-tier Air Conditioned sleeper"),
    THIRD_AC("3A", "Third AC", "Three-tier Air Conditioned sleeper"),
    AC_ECONOMY("3E", "AC 3 Economy", "Three-tier Economy Air Conditioned"),
    SLEEPER("SL", "Sleeper", "Non-AC sleeper class"),
    CHAIR_CAR("CC", "AC Chair Car", "Air Conditioned seating"),
    EXEC_CHAIR("EC", "Executive Chair Car", "Premium AC seating"),
    SECOND_SITTING("2S", "Second Sitting", "Non-AC seating class"),
    GENERAL("GN", "General", "Unreserved general compartment");

    private final String code;
    private final String displayName;
    private final String description;

    CoachClass(String code, String displayName, String description) {
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

    public static CoachClass fromCode(String code) {
        for (CoachClass coachClass : values()) {
            if (coachClass.code.equalsIgnoreCase(code)) {
                return coachClass;
            }
        }
        throw new IllegalArgumentException("Unknown coach class code: " + code);
    }
}
