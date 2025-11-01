package co.edu.umanizales.eco_move.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String id;
    private String userId;
    private String vehicleId;
    private String reservationId;
    private int rating; // 1-5 stars
    private String comment;
    private LocalDateTime createdAt;
    private boolean isVerified;
    private int helpfulCount;
    
    public Review(String userId, String vehicleId, String reservationId, int rating, String comment) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.reservationId = reservationId;
        this.rating = Math.max(1, Math.min(5, rating)); // Ensure rating is between 1-5
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.isVerified = false;
        this.helpfulCount = 0;
    }
    
    public void verify() {
        this.isVerified = true;
    }
    
    public void markAsHelpful() {
        this.helpfulCount++;
    }
    
    public boolean isPositive() {
        return rating >= 4;
    }
    
    public boolean isNegative() {
        return rating <= 2;
    }
    
    public boolean isNeutral() {
        return rating == 3;
    }
}
