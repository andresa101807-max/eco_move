package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.VehicleType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryDrone extends Vehicle {
    private double maxPayload;
    private double maxAltitude;
    private boolean autoPilotEnabled;
    
    public DeliveryDrone(String name, String model, String brand, double pricePerHour,
                        double maxPayload, double maxAltitude) {
        super(name, model, brand, VehicleType.DELIVERY_DRONE, pricePerHour);
        this.maxPayload = maxPayload;
        this.maxAltitude = maxAltitude;
        this.autoPilotEnabled = false;
        this.battery = new Battery(200, 200, 0, 500);
    }
    
    public DeliveryDrone() {
        super();
        this.battery = new Battery(200, 200, 0, 500);
        this.autoPilotEnabled = false;
    }
    
    @Override
    public double calculateRentalCost(int minutes) {
        return (minutes / 60.0) * pricePerHour * 2; // Drones are premium
    }
    
    @Override
    public double getMaxSpeed() {
        return 60.0; // km/h
    }
    
    @Override
    public double getRange() {
        if (battery == null) {
            return 0.0;
        }
        return (battery.getCurrentCharge() / battery.getCapacity()) * 20; // 20 km max range
    }
    
}
