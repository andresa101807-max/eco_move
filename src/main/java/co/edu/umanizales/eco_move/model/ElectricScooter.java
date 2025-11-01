package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.VehicleType;
import co.edu.umanizales.eco_move.model.interfaces.IoTEnabled;
import co.edu.umanizales.eco_move.model.interfaces.Rechargeable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ElectricScooter extends Vehicle implements Rechargeable, IoTEnabled {
    private boolean hasSuspension;
    private double maxLoad;
    private Map<String, Object> sensorData;
    
    public ElectricScooter(String name, String model, String brand, double pricePerHour,
                          boolean hasSuspension, double maxLoad) {
        super(name, model, brand, VehicleType.ELECTRIC_SCOOTER, pricePerHour);
        this.hasSuspension = hasSuspension;
        this.maxLoad = maxLoad;
        this.battery = new Battery(350, 350, 0, 800);
        this.sensorData = new HashMap<>();
        initializeSensors();
    }
    
    public ElectricScooter() {
        super();
        this.battery = new Battery(350, 350, 0, 800);
        this.sensorData = new HashMap<>();
        initializeSensors();
    }
    
    private void initializeSensors() {
        sensorData.put("speed", 0.0);
        sensorData.put("temperature", 25.0);
        sensorData.put("pressure", 2.5);
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
        return battery != null && battery.getChargePercentage() < 20;
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
