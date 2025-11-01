package co.edu.umanizales.eco_move.model.interfaces;

import java.time.LocalDateTime;

/**
 * Interface for entities that can be rented
 */
public interface Rentable {
    boolean isAvailableForRent();
    
    double calculateRentalCost(LocalDateTime startTime, LocalDateTime endTime);
    
    void startRental(String userId);
    
    void endRental();
    
    String getCurrentRenterId();
    
    LocalDateTime getRentalStartTime();
}
