package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.VehicleStatus;
import co.edu.umanizales.eco_move.model.enums.VehicleType;
import co.edu.umanizales.eco_move.model.records.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Vehicle {
    protected String id;
    protected String name;
    protected String model;
    protected String brand;
    protected VehicleType type;
    protected VehicleStatus status;
    protected double pricePerHour;
    protected Coordinates currentLocation;
    protected Battery battery;
    
    public Vehicle(String name, String model, String brand, VehicleType type, double pricePerHour) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.status = VehicleStatus.AVAILABLE;
        this.pricePerHour = pricePerHour;
        this.currentLocation = new Coordinates(0.0, 0.0);
    }
    
    public abstract double calculateRentalCost(int minutes);
    
    public abstract double getMaxSpeed();
    
    public abstract double getRange();
    
    public boolean isAvailable() {
        return status == VehicleStatus.AVAILABLE;
    }
    
    public void reserve() {
        if (isAvailable()) {
            status = VehicleStatus.RESERVED;
        }
    }
    
    public void startTrip() {
        if (status == VehicleStatus.RESERVED || isAvailable()) {
            status = VehicleStatus.IN_USE;
        }
    }
    
    public void endTrip() {
        if (status == VehicleStatus.IN_USE) {
            status = VehicleStatus.AVAILABLE;
        }
    }
    
    public void sendToMaintenance() {
        status = VehicleStatus.MAINTENANCE;
    }
    
    public void makeAvailable() {
        status = VehicleStatus.AVAILABLE;
    }
}
