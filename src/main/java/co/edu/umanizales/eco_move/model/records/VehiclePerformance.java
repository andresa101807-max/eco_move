package co.edu.umanizales.eco_move.model.records;

/**
 * Immutable record representing vehicle performance metrics
 */
public record VehiclePerformance(
    String vehicleId,
    double totalDistanceTraveled,
    int totalTrips,
    double averageTripDistance,
    double totalRevenue,
    int maintenanceCount,
    double utilizationRate,
    double averageRating
) {
    public boolean isHighPerformer() {
        return utilizationRate > 70 && averageRating >= 4.0;
    }
    
    public double getRevenuePerTrip() {
        if (totalTrips > 0) {
            return totalRevenue / totalTrips;
        }
        return 0.0;
    }
    
    public double getRevenuePerKm() {
        if (totalDistanceTraveled > 0) {
            return totalRevenue / totalDistanceTraveled;
        }
        return 0.0;
    }
    
    public boolean needsMaintenance() {
        return totalDistanceTraveled > 1000 || totalTrips > 100;
    }
}
