package com.irctc.booking.model;

/**
 * Types of trains in Indian Railways
 */
public enum TrainType {
    RAJDHANI("Rajdhani Express", "Premium AC train connecting major cities to Delhi"),
    SHATABDI("Shatabdi Express", "Premium day travel superfast train"),
    DURONTO("Duronto Express", "Non-stop long distance train"),
    GARIB_RATH("Garib Rath", "Budget AC train"),
    TEJAS("Tejas Express", "Premium train with modern amenities"),
    VANDE_BHARAT("Vande Bharat Express", "India's first semi-high speed train"),
    HUMSAFAR("Humsafar Express", "Fully AC 3-tier train"),
    SUPERFAST("Superfast Express", "High speed mail/express train"),
    EXPRESS("Express", "Regular express train"),
    MAIL("Mail", "Mail train"),
    PASSENGER("Passenger", "Local passenger train"),
    LOCAL("Local/Suburban", "Urban suburban train"),
    SPECIAL("Special", "Special/Holiday train");

    private final String displayName;
    private final String description;

    TrainType(String displayName, String description) {
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
