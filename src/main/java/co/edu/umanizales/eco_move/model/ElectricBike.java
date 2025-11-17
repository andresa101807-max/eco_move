package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.VehicleType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ElectricBike extends Vehicle {
    private int gears;
    private boolean hasLights;
    private double weight;
    
    public ElectricBike(String name, String model, String brand, double pricePerHour, 
                       int gears, boolean hasLights, double weight) {
        super(name, model, brand, VehicleType.ELECTRIC_BIKE, pricePerHour);
        this.gears = gears;
        this.hasLights = hasLights;
        this.weight = weight;
        this.battery = new Battery(500, 500, 0, 1000);
    }
    
    public ElectricBike() {
        super();
        this.battery = new Battery(500, 500, 0, 1000);
    }
    
    @Override
    public double calculateRentalCost(int minutes) {
        return (minutes / 60.0) * pricePerHour;
    }
    
    @Override
    public double getMaxSpeed() {
        return 25.0; // km/h
    }
    
    @Override
    public double getRange() {
        if (battery == null) {
            return 0.0;
        }
        return (battery.getCurrentCharge() / battery.getCapacity()) * 50; // 50 km max range
    }
}
