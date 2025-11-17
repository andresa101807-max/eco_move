package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.VehicleStatus;
import co.edu.umanizales.eco_move.model.enums.VehicleType;
import co.edu.umanizales.eco_move.model.records.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Clase base (abstracta) para todos los vehículos del sistema.
 *
 * Qué significa abstracta: no se crean objetos de Vehicle directamente,
 * solo de sus clases hijas (por ejemplo, ElectricBike).
 *
 * Esta clase define los datos comunes y algunos comportamientos generales
 * que comparten todos los vehículos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Vehicle {
    // Identificador único del vehículo
    protected String id;
    // Nombre comercial del vehículo (ej.: "Bici Urbana")
    protected String name;
    // Modelo y marca ayudan a identificar el vehículo
    protected String model;
    protected String brand;
    // Tipo y estado del vehículo (ej.: ELECTRIC_BIKE, AVAILABLE)
    protected VehicleType type;
    protected VehicleStatus status;
    // Precio por hora de alquiler
    protected double pricePerHour;
    // Ubicación actual del vehículo (latitud, longitud)
    protected Coordinates currentLocation;
    // Batería del vehículo (nivel de carga, capacidad, etc.)
    protected Battery battery;
    
    /**
     * Constructor pensado para que las clases hijas (bicicleta, scooter, etc.)
     * inicialicen los datos comunes de un vehículo.
     */
    public Vehicle(String name, String model, String brand, VehicleType type, double pricePerHour) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.status = VehicleStatus.AVAILABLE; // Por defecto el vehículo inicia disponible
        this.pricePerHour = pricePerHour;
        this.currentLocation = new Coordinates(0.0, 0.0); // Posición por defecto
    }
    
    /**
     * Calcula el costo de alquiler según los minutos.
     * Cada tipo de vehículo define su propia fórmula.
     */
    public abstract double calculateRentalCost(int minutes);
    
    /**
     * Devuelve la velocidad máxima aproximada del vehículo (km/h).
     */
    public abstract double getMaxSpeed();
    
    /**
     * Devuelve la autonomía aproximada (km) con la carga actual.
     */
    public abstract double getRange();
    
    /**
     * Indica si el vehículo está disponible para alquilar.
     */
    public boolean isAvailable() {
        return status == VehicleStatus.AVAILABLE;
    }
    
    /**
     * Marca el vehículo como reservado (si estaba disponible).
     */
    public void reserve() {
        if (isAvailable()) {
            status = VehicleStatus.RESERVED;
        }
    }
    
    /**
     * Comienza un viaje: pasa a estado "EN_USO" si estaba reservado o disponible.
     */
    public void startTrip() {
        if (status == VehicleStatus.RESERVED || isAvailable()) {
            status = VehicleStatus.IN_USE;
        }
    }
    
    /**
     * Finaliza el viaje y vuelve el vehículo a "DISPONIBLE".
     */
    public void endTrip() {
        if (status == VehicleStatus.IN_USE) {
            status = VehicleStatus.AVAILABLE;
        }
    }
    
    /**
     * Envía el vehículo a mantenimiento.
     */
    public void sendToMaintenance() {
        status = VehicleStatus.MAINTENANCE;
    }
    
    /**
     * Deja el vehículo disponible nuevamente.
     */
    public void makeAvailable() {
        status = VehicleStatus.AVAILABLE;
    }
}
