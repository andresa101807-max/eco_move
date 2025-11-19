package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.eco_move.model.enums.VehicleStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private String id;
    private String userId;
    private String vehicleId;
    private LocalDateTime reservationTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus status;
    private double estimatedCost;
    private double actualCost;
    
    public Reservation(String userId, String vehicleId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.reservationTime = LocalDateTime.now();
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = ReservationStatus.PENDING;
        this.estimatedCost = 0.0;
        this.actualCost = 0.0;
    }
    
    /**
     * Enlaza el usuario a la reserva asignando su ID con una validación básica.
     */
    public boolean linkUser(User user) {
        if (user == null || !user.isActive()) {
            return false;
        }
        this.userId = user.getId();
        return true;
    }
    
    /**
     * Enlaza el vehículo a la reserva si está disponible.
     */
    public boolean linkVehicle(Vehicle vehicle) {
        if (vehicle == null || vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            return false;
        }
        this.vehicleId = vehicle.getId();
        return true;
    }
    
    public void confirm() {
        if (status == ReservationStatus.PENDING) {
            status = ReservationStatus.CONFIRMED;
        }
    }
    
    public void start() {
        if (status == ReservationStatus.CONFIRMED) {
            status = ReservationStatus.IN_PROGRESS;
        }
    }
    
    /**
     * Variante de start que actualiza el estado del vehículo a IN_USE si procede.
     */
    public boolean start(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        if (status == ReservationStatus.CONFIRMED
                && (vehicle.getStatus() == VehicleStatus.RESERVED || vehicle.getStatus() == VehicleStatus.AVAILABLE)) {
            status = ReservationStatus.IN_PROGRESS;
            vehicle.startTrip();
            return true;
        }
        return false;
    }
    
    public void complete(double cost) {
        if (status == ReservationStatus.IN_PROGRESS) {
            status = ReservationStatus.COMPLETED;
            this.actualCost = cost;
        }
    }
    
    /**
     * Variante de complete que devuelve el vehículo a AVAILABLE.
     */
    public boolean complete(double cost, Vehicle vehicle) {
        if (status == ReservationStatus.IN_PROGRESS && vehicle != null) {
            status = ReservationStatus.COMPLETED;
            this.actualCost = cost;
            vehicle.endTrip();
            return true;
        }
        return false;
    }
    
    public void cancel() {
        if (status != ReservationStatus.COMPLETED) {
            status = ReservationStatus.CANCELLED;
        }
    }

    /**
     * Cancela la reserva y, si corresponde, revierte el estado del vehículo a AVAILABLE.
     */
    public boolean cancel(Vehicle vehicle) {
        if (status == ReservationStatus.COMPLETED) {
            return false;
        }
        status = ReservationStatus.CANCELLED;
        if (vehicle != null) {
            // Si el vehículo estaba reservado o en uso por esta reserva, devuélvelo a disponible
            switch (vehicle.getStatus()) {
                case IN_USE:
                case RESERVED:
                    vehicle.makeAvailable();
                    break;
                default:
                    // no-op
            }
        }
        return true;
    }
}

