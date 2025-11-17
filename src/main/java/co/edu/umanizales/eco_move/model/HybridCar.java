package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.VehicleType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HybridCar extends Vehicle {
    private int seats;
    private double fuelCapacity;
    private double currentFuel;
    private boolean hasAirConditioning;
    
    public HybridCar(String name, String model, String brand, double pricePerHour,
                    int seats, double fuelCapacity, boolean hasAirConditioning) {
        super(name, model, brand, VehicleType.HYBRID_CAR, pricePerHour);
        this.seats = seats;
        this.fuelCapacity = fuelCapacity;
        this.currentFuel = fuelCapacity;
        this.hasAirConditioning = hasAirConditioning;
        this.battery = new Battery(1500, 1500, 0, 2000);
    }
    
    public HybridCar() {
        super();
        this.battery = new Battery(1500, 1500, 0, 2000);
        this.fuelCapacity = 45.0;
        this.currentFuel = 45.0;
    }
    
    @Override
    public double calculateRentalCost(int minutes) {
        double baseCost = (minutes / 60.0) * pricePerHour;
        return baseCost * 1.5; // Cars are more expensive
    }
    
    @Override
    public double getMaxSpeed() {
        return 120.0; // km/h
    }
    
    @Override
    public double getRange() {
        double electricRange = 0.0;
        if (battery != null) {
            electricRange = (battery.getCurrentCharge() / battery.getCapacity()) * 50;
        }
        double fuelRange = (currentFuel / fuelCapacity) * 400;
        return electricRange + fuelRange;
    }
    
}
