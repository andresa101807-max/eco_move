package co.edu.umanizales.eco_move.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battery {
    // Capacidad total de la batería en Wh (vatios-hora)
    private double capacity; // in Wh
    // Carga actual en Wh
    private double currentCharge; // in Wh
    // Número de cargas realizadas (ciclos)
    private int chargeCycles;
    // Máximo de ciclos recomendado por el fabricante
    private int maxChargeCycles;
    
    /**
     * Carga la batería en la cantidad indicada.
     * No permite superar la capacidad máxima.
     * Aumenta el contador de ciclos.
     */
    public void charge(double amount) {
        if (currentCharge + amount <= capacity) {
            currentCharge += amount;
        } else {
            currentCharge = capacity;
        }
        chargeCycles++;
    }
    
    /**
     * Intenta descargar la batería en la cantidad indicada.
     * Devuelve true si fue posible (había suficiente carga).
     */
    public boolean discharge(double amount) {
        if (currentCharge >= amount) {
            currentCharge -= amount;
            return true;
        }
        return false;
    }
    
    /**
     * Porcentaje de carga (0 a 100).
     */
    public double getChargePercentage() {
        return (currentCharge / capacity) * 100;
    }
    
    /**
     * Indica si la batería debería considerarse para reemplazo
     * cuando ya ha alcanzado ~90% de su vida útil de ciclos.
     */
    public boolean needsReplacement() {
        return chargeCycles >= maxChargeCycles * 0.9;
    }
    
    /**
     * Serializa la batería a una cadena simple
     * para guardarla en CSV: capacity,currentCharge,chargeCycles,maxChargeCycles
     */
    @Override
    public String toString() {
        return capacity + "," + currentCharge + "," + chargeCycles + "," + maxChargeCycles;
    }
    
    /**
     * Crea una batería a partir de su representación en cadena
     * usando el mismo formato que toString().
     */
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
