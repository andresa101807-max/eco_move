package co.edu.umanizales.eco_move.model.records;

public record UsageStatistics(
    int totalTrips,
    double totalDistance,
    double totalDuration,
    double averageSpeed,
    double co2Saved
) {
    @Override
    public String toString() {
        return totalTrips + "," + totalDistance + "," + totalDuration + "," + averageSpeed + "," + co2Saved;
    }
    
    public static UsageStatistics fromString(String str) {
        String[] parts = str.split(",");
        return new UsageStatistics(
            Integer.parseInt(parts[0]),
            Double.parseDouble(parts[1]),
            Double.parseDouble(parts[2]),
            Double.parseDouble(parts[3]),
            Double.parseDouble(parts[4])
        );
    }
}
