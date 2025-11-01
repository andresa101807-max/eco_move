package co.edu.umanizales.eco_move.model.records;

import co.edu.umanizales.eco_move.model.enums.PaymentMethod;

import java.time.LocalDateTime;

/**
 * Immutable record representing payment information
 */
public record PaymentInfo(
    String paymentId,
    String transactionId,
    double amount,
    PaymentMethod method,
    LocalDateTime timestamp,
    boolean isSuccessful
) {
    public String getFormattedAmount() {
        return String.format("$%.2f", amount);
    }
    
    public boolean isRecent() {
        return timestamp.isAfter(LocalDateTime.now().minusDays(7));
    }
    
    public String getPaymentSummary() {
        return String.format("%s - %s - %s", 
            getFormattedAmount(), 
            method, 
            isSuccessful ? "SUCCESS" : "FAILED"
        );
    }
}
