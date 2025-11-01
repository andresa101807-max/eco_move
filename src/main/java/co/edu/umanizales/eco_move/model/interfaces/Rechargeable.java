package co.edu.umanizales.eco_move.model.interfaces;

public interface Rechargeable {
    void charge(double amount);
    double getBatteryLevel();
    double getMaxBatteryCapacity();
    boolean needsCharging();
}
