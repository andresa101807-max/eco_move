package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.PaymentMethod;
import co.edu.umanizales.eco_move.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String id;
    private String userId;
    private String reservationId;
    private double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private String transactionId;
    private String description;
    
    public Payment(String userId, String reservationId, double amount, PaymentMethod paymentMethod) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = PaymentStatus.PENDING;
        this.paymentDate = LocalDateTime.now();
        this.transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    public void approve() {
        this.status = PaymentStatus.APPROVED;
    }
    
    public void reject() {
        this.status = PaymentStatus.REJECTED;
    }
    
    public void refund() {
        this.status = PaymentStatus.REFUNDED;
    }
    
    public boolean isSuccessful() {
        return status == PaymentStatus.APPROVED;
    }
}
