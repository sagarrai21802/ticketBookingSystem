package com.irctc.booking.model;

/**
 * ID proof types for verification
 */
public enum IdProofType {
    AADHAAR("Aadhaar Card"),
    PAN("PAN Card"),
    PASSPORT("Passport"),
    VOTER_ID("Voter ID Card"),
    DRIVING_LICENSE("Driving License"),
    STUDENT_ID("Student ID Card"),
    GOVT_ID("Government ID");

    private final String displayName;

    IdProofType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
