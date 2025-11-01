package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.records.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private String id;
    private String name;
    private String description;
    private Coordinates startPoint;
    private Coordinates endPoint;
    private List<Coordinates> waypoints;
    private double estimatedDistance;
    private int estimatedDuration; // in minutes
    private String difficulty; // easy, medium, hard
    
    public Route(String name, String description, Coordinates startPoint, Coordinates endPoint,
                String difficulty) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.waypoints = new ArrayList<>();
        this.difficulty = difficulty;
        this.estimatedDistance = startPoint.distanceTo(endPoint);
        this.estimatedDuration = (int) (estimatedDistance * 3); // Rough estimate: 3 min per km
    }
    
    public void addWaypoint(Coordinates waypoint) {
        waypoints.add(waypoint);
        recalculateDistance();
    }
    
    private void recalculateDistance() {
        double totalDistance = 0;
        Coordinates current = startPoint;
        
        for (Coordinates waypoint : waypoints) {
            totalDistance += current.distanceTo(waypoint);
            current = waypoint;
        }
        
        totalDistance += current.distanceTo(endPoint);
        this.estimatedDistance = totalDistance;
        this.estimatedDuration = (int) (estimatedDistance * 3);
    }
}
