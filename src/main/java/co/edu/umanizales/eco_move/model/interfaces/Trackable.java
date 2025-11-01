package co.edu.umanizales.eco_move.model.interfaces;

import co.edu.umanizales.eco_move.model.records.Coordinates;

import java.time.LocalDateTime;

/**
 * Interface for entities that can be tracked in real-time
 */
public interface Trackable {
    Coordinates getCurrentLocation();
    
    void updateLocation(Coordinates newLocation);
    
    LocalDateTime getLastLocationUpdate();
    
    double getDistanceTraveled();
    
    boolean isMoving();
}
