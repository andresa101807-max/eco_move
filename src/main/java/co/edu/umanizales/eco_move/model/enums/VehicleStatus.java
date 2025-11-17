package co.edu.umanizales.eco_move.model.enums;

/**
 * Estados posibles de un vehículo dentro del sistema.
 * Ayudan a controlar su ciclo de vida durante el alquiler.
 */
public enum VehicleStatus {
    AVAILABLE,   // Disponible para alquilar
    IN_USE,      // Actualmente en uso (en viaje)
    MAINTENANCE, // En mantenimiento
    RESERVED,    // Reservado por un usuario
    UNAVAILABLE  // No disponible por alguna razón
}
