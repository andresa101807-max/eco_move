package co.edu.umanizales.eco_move.model.records;

import java.time.LocalDateTime;

/**
 * Immutable record representing a summary of a completed trip
 */
public record TripSummary(
    String tripId,
    String userId,
    String vehicleId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    double distance,
    double cost,
    int durationMinutes,
    Coordinates startLocation,
    Coordinates endLocation
) {
    public double getAverageSpeed() {
        if (durationMinutes > 0) {
            return (distance / durationMinutes) * 60; // km/h
        }
        return 0.0;
    }
    
    public boolean isLongTrip() {
        return durationMinutes > 60;
    }
    
    public double getCostPerKm() {
        if (distance > 0) {
            return cost / distance;
        }
        return 0.0;
    }
}
