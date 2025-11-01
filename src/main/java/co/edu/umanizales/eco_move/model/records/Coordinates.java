package co.edu.umanizales.eco_move.model.records;

public record Coordinates(double latitude, double longitude) {
    
    public double distanceTo(Coordinates other) {
        // Haversine formula for calculating distance between two coordinates
        final int EARTH_RADIUS = 6371; // kilometers
        
        double latDistance = Math.toRadians(other.latitude - this.latitude);
        double lonDistance = Math.toRadians(other.longitude - this.longitude);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS * c;
    }
    
    @Override
    public String toString() {
        return latitude + "," + longitude;
    }
    
    public static Coordinates fromString(String str) {
        String[] parts = str.split(",");
        return new Coordinates(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
    }
}
