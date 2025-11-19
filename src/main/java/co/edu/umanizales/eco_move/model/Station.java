package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.StationType;
import co.edu.umanizales.eco_move.model.records.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.eco_move.model.enums.VehicleStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Station {
    private String id;
    private String name;
    private Coordinates location;
    private StationType type;
    private int capacity;
    private int availableSpots;
    private List<String> vehicleIds;
    private boolean hasChargingStations;
    private String operatingHours;
    private boolean isActive;
    
    public Station(String name, Coordinates location, StationType type, int capacity, boolean hasChargingStations) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.location = location;
        this.type = type;
        this.capacity = capacity;
        this.availableSpots = capacity;
        this.vehicleIds = new ArrayList<>();
        this.hasChargingStations = hasChargingStations;
        this.operatingHours = "24/7";
        this.isActive = true;
    }
    
    public boolean hasAvailableSpots() {
        return availableSpots > 0;
    }
    
    public boolean addVehicle(String vehicleId) {
        if (hasAvailableSpots() && !vehicleIds.contains(vehicleId)) {
            vehicleIds.add(vehicleId);
            availableSpots--;
            return true;
        }
        return false;
    }
    
    /**
     * Helper de relación: agrega un vehículo validando su estado.
     * Solo admite vehículos en estado AVAILABLE.
     */
    public boolean addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            return false;
        }
        return addVehicle(vehicle.getId());
    }
    
    public boolean removeVehicle(String vehicleId) {
        if (vehicleIds.remove(vehicleId)) {
            availableSpots++;
            return true;
        }
        return false;
    }
    
    public int getOccupiedSpots() {
        return capacity - availableSpots;
    }
    
    public double getOccupancyRate() {
        return (double) getOccupiedSpots() / capacity * 100;
    }
}

