package co.edu.umanizales.eco_move.model.records;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
    private double latitude;
    private double longitude;

    public static Coordinates fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        String cleaned = value.trim();
        if (cleaned.startsWith("(") && cleaned.endsWith(")")) {
            cleaned = cleaned.substring(1, cleaned.length() - 1);
        }
        String[] parts = cleaned.split(",");
        double lat = Double.parseDouble(parts[0].trim());
        double lon = Double.parseDouble(parts[1].trim());
        return new Coordinates(lat, lon);
    }

    public double distanceTo(Coordinates other) {
        if (other == null) return 0.0;
        // Haversine formula (distance in kilometers)
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(other.latitude - this.latitude);
        double lonDistance = Math.toRadians(other.longitude - this.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    @Override
    public String toString() {
        return this.latitude + "," + this.longitude;
    }
}
