package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.VehicleType;
import co.edu.umanizales.eco_move.model.interfaces.Autonomous;
import co.edu.umanizales.eco_move.model.interfaces.IoTEnabled;
import co.edu.umanizales.eco_move.model.interfaces.Rechargeable;
import co.edu.umanizales.eco_move.model.records.Coordinates;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryDrone extends Vehicle implements Rechargeable, Autonomous, IoTEnabled {
    private double maxPayload;
    private double maxAltitude;
    private boolean autoPilotEnabled;
    private Coordinates destination;
    private Map<String, Object> sensorData;
    
    public DeliveryDrone(String name, String model, String brand, double pricePerHour,
                        double maxPayload, double maxAltitude) {
        super(name, model, brand, VehicleType.DELIVERY_DRONE, pricePerHour);
        this.maxPayload = maxPayload;
        this.maxAltitude = maxAltitude;
        this.autoPilotEnabled = false;
        this.battery = new Battery(200, 200, 0, 500);
        this.sensorData = new HashMap<>();
        initializeSensors();
    }
    
    public DeliveryDrone() {
        super();
        this.battery = new Battery(200, 200, 0, 500);
        this.sensorData = new HashMap<>();
        this.autoPilotEnabled = false;
        initializeSensors();
    }
    
    private void initializeSensors() {
        sensorData.put("altitude", 0.0);
        sensorData.put("windSpeed", 0.0);
        sensorData.put("temperature", 25.0);
        sensorData.put("gps", "active");
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
    
    @Override
    public void charge(double amount) {
        if (battery != null) {
            battery.charge(amount);
        }
    }
    
    @Override
    public double getBatteryLevel() {
        return battery != null ? battery.getCurrentCharge() : 0.0;
    }
    
    @Override
    public double getMaxBatteryCapacity() {
        return battery != null ? battery.getCapacity() : 0.0;
    }
    
    @Override
    public boolean needsCharging() {
        return battery != null && battery.getChargePercentage() < 30;
    }
    
    @Override
    public void setDestination(Coordinates destination) {
        this.destination = destination;
    }
    
    @Override
    public Coordinates getCurrentPosition() {
        return currentLocation;
    }
    
    @Override
    public boolean isAutoPilotEnabled() {
        return autoPilotEnabled;
    }
    
    @Override
    public void enableAutoPilot() {
        this.autoPilotEnabled = true;
    }
    
    @Override
    public void disableAutoPilot() {
        this.autoPilotEnabled = false;
    }
    
    @Override
    public Map<String, Object> getSensorData() {
        return new HashMap<>(sensorData);
    }
    
    @Override
    public void updateSensorData(String sensorName, Object value) {
        sensorData.put(sensorName, value);
    }
    
    @Override
    public boolean isSensorActive(String sensorName) {
        return sensorData.containsKey(sensorName);
    }
}
