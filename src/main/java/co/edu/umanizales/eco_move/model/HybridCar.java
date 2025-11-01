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
public class HybridCar extends Vehicle implements Rechargeable, IoTEnabled {
    private int seats;
    private double fuelCapacity;
    private double currentFuel;
    private boolean hasAirConditioning;
    private Map<String, Object> sensorData;
    
    public HybridCar(String name, String model, String brand, double pricePerHour,
                    int seats, double fuelCapacity, boolean hasAirConditioning) {
        super(name, model, brand, VehicleType.HYBRID_CAR, pricePerHour);
        this.seats = seats;
        this.fuelCapacity = fuelCapacity;
        this.currentFuel = fuelCapacity;
        this.hasAirConditioning = hasAirConditioning;
        this.battery = new Battery(1500, 1500, 0, 2000);
        this.sensorData = new HashMap<>();
        initializeSensors();
    }
    
    public HybridCar() {
        super();
        this.battery = new Battery(1500, 1500, 0, 2000);
        this.sensorData = new HashMap<>();
        this.fuelCapacity = 45.0;
        this.currentFuel = 45.0;
        initializeSensors();
    }
    
    private void initializeSensors() {
        sensorData.put("speed", 0.0);
        sensorData.put("fuelLevel", currentFuel);
        sensorData.put("engineTemp", 90.0);
        sensorData.put("tirePressure", 32.0);
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
        return battery != null && battery.getChargePercentage() < 20 && (currentFuel / fuelCapacity) < 0.2;
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
