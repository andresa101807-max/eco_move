package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.VehicleType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ElectricScooter extends Vehicle {
    private boolean hasSuspension;
    private double maxLoad;
    
    public ElectricScooter(String name, String model, String brand, double pricePerHour,
                          boolean hasSuspension, double maxLoad) {
        super(name, model, brand, VehicleType.ELECTRIC_SCOOTER, pricePerHour);
        this.hasSuspension = hasSuspension;
        this.maxLoad = maxLoad;
        this.battery = new Battery(350, 350, 0, 800);
    }
    
    public ElectricScooter() {
        super();
        this.battery = new Battery(350, 350, 0, 800);
    }
    
    @Override
    public double calculateRentalCost(int minutes) {
        return (minutes / 60.0) * pricePerHour;
    }
    
    @Override
    public double getMaxSpeed() {
        return 20.0; // km/h
    }
    
    @Override
    public double getRange() {
        if (battery == null) {
            return 0.0;
        }
        return (battery.getCurrentCharge() / battery.getCapacity()) * 30; // 30 km max range
    }
}
