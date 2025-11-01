package co.edu.umanizales.eco_move.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripHistory {
    private List<Trip> trips = new ArrayList<>();
    
    public void addTrip(Trip trip) {
        trips.add(trip);
    }
    
    public int getTotalTrips() {
        return trips.size();
    }
    
    public double getTotalDistance() {
        return trips.stream().mapToDouble(Trip::getDistance).sum();
    }
    
    public double getTotalCost() {
        return trips.stream().mapToDouble(Trip::getCost).sum();
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Trip {
        private String tripId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private double distance;
        private double cost;
        
        @Override
        public String toString() {
            return tripId + ";" + startTime + ";" + endTime + ";" + distance + ";" + cost;
        }
        
        public static Trip fromString(String str) {
            String[] parts = str.split(";");
            return new Trip(
                parts[0],
                LocalDateTime.parse(parts[1]),
                LocalDateTime.parse(parts[2]),
                Double.parseDouble(parts[3]),
                Double.parseDouble(parts[4])
            );
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Trip trip : trips) {
            sb.append(trip.toString()).append("|");
        }
        return sb.toString();
    }
    
    public static TripHistory fromString(String str) {
        TripHistory history = new TripHistory();
        if (str != null && !str.isEmpty()) {
            String[] tripStrings = str.split("\\|");
            for (String tripStr : tripStrings) {
                if (!tripStr.isEmpty()) {
                    history.addTrip(Trip.fromString(tripStr));
                }
            }
        }
        return history;
    }
}
