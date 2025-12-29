package com.irctc.booking.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for generating PNR numbers
 * Indian Railway PNR format: 10-digit number
 */
public class PnrGenerator {

    private static final SecureRandom random = new SecureRandom();
    
    /**
     * Generate a 10-digit PNR number
     * Format: First 3 digits represent source zone, rest are random
     */
    public static String generatePnr() {
        // First digit: 1-9
        StringBuilder pnr = new StringBuilder();
        pnr.append(random.nextInt(9) + 1);
        
        // Remaining 9 digits: 0-9
        for (int i = 0; i < 9; i++) {
            pnr.append(random.nextInt(10));
        }
        
        return pnr.toString();
    }
    
    /**
     * Generate a transaction ID
     * Format: TXN + Date + Random 8 digits
     */
    public static String generateTransactionId() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        StringBuilder txnId = new StringBuilder("TXN");
        txnId.append(dateStr);
        
        for (int i = 0; i < 8; i++) {
            txnId.append(random.nextInt(10));
        }
        
        return txnId.toString();
    }
}
