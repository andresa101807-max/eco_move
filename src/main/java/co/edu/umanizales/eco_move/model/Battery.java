package co.edu.umanizales.eco_move.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battery {
    private double capacity; // in Wh
    private double currentCharge; // in Wh
    private int chargeCycles;
    private int maxChargeCycles;
    
    public void charge(double amount) {
        if (currentCharge + amount <= capacity) {
            currentCharge += amount;
        } else {
            currentCharge = capacity;
        }
        chargeCycles++;
    }
    
    public boolean discharge(double amount) {
        if (currentCharge >= amount) {
            currentCharge -= amount;
            return true;
        }
        return false;
    }
    
    public double getChargePercentage() {
        return (currentCharge / capacity) * 100;
    }
    
    public boolean needsReplacement() {
        return chargeCycles >= maxChargeCycles * 0.9;
    }
    
    @Override
    public String toString() {
        return capacity + "," + currentCharge + "," + chargeCycles + "," + maxChargeCycles;
    }
    
    public static Battery fromString(String str) {
        String[] parts = str.split(",");
        return new Battery(
            Double.parseDouble(parts[0]),
            Double.parseDouble(parts[1]),
            Integer.parseInt(parts[2]),
            Integer.parseInt(parts[3])
        );
    }
}
