package com.irctc.booking.model;

/**
 * Payment methods available
 */
public enum PaymentMethod {
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    NET_BANKING("Net Banking"),
    UPI("UPI (Unified Payments Interface)"),
    PAYTM("Paytm Wallet"),
    PHONEPE("PhonePe"),
    GOOGLE_PAY("Google Pay"),
    AMAZON_PAY("Amazon Pay"),
    IRCTC_WALLET("IRCTC eWallet"),
    BHIM("BHIM");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
