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
    // Lista de viajes del usuario (historial sencillo)
    private List<Trip> trips = new ArrayList<>();
    
    /**
     * Agrega un viaje al historial.
     */
    public void addTrip(Trip trip) {
        trips.add(trip);
    }
    
    /**
     * Total de viajes registrados.
     */
    public int getTotalTrips() {
        return trips.size();
    }
    
    /**
     * Suma de la distancia de todos los viajes.
     */
    public double getTotalDistance() {
        return trips.stream().mapToDouble(Trip::getDistance).sum();
    }
    
    /**
     * Suma del costo de todos los viajes.
     */
    public double getTotalCost() {
        return trips.stream().mapToDouble(Trip::getCost).sum();
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Trip {
        // Identificador del viaje (cualquier formato de id)
        private String tripId;
        // Inicio y fin del viaje
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        // Datos simples del viaje
        private double distance; // en km
        private double cost;     // en moneda local
        
        /**
         * Convierte el viaje a un string simple separado por ";"
         * para guardarlo en CSV junto con el usuario.
         */
        @Override
        public String toString() {
            return tripId + ";" + startTime + ";" + endTime + ";" + distance + ";" + cost;
        }
        
        /**
         * Crea un viaje a partir del formato generado por toString().
         */
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
    
    /**
     * Serializa todo el historial a una cadena:
     * cada viaje como toString() y separados por "|".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Trip trip : trips) {
            sb.append(trip.toString()).append("|");
        }
        return sb.toString();
    }
    
    /**
     * Reconstruye el historial desde una cadena generada por toString().
     */
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
