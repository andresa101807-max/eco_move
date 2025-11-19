package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.IncidentSeverity;
import co.edu.umanizales.eco_move.model.enums.IncidentStatus;
import co.edu.umanizales.eco_move.model.enums.IncidentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Incident {
    private String id;
    private String vehicleId;
    private String userId;
    private String reservationId;
    private IncidentType type;
    private IncidentSeverity severity;
    private IncidentStatus status;
    private String description;
    private LocalDateTime reportedAt;
    private LocalDateTime resolvedAt;
    private String resolvedBy;
    private String resolution;
    
    public Incident(String vehicleId, String userId, IncidentType type, IncidentSeverity severity, String description) {
        this.id = UUID.randomUUID().toString();
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.type = type;
        this.severity = severity;
        this.status = IncidentStatus.REPORTED;
        this.description = description;
        this.reportedAt = LocalDateTime.now();
    }
    
    /**
     * Enlaza una reserva al incidente validando consistencia con userId y vehicleId.
     */
    public boolean attachReservation(Reservation reservation) {
        if (reservation == null) {
            return false;
        }
        // Si el incidente ya tiene user/vehicle seteados, deben coincidir.
        if (this.userId != null && reservation.getUserId() != null && !this.userId.equals(reservation.getUserId())) {
            return false;
        }
        if (this.vehicleId != null && reservation.getVehicleId() != null && !this.vehicleId.equals(reservation.getVehicleId())) {
            return false;
        }
        this.reservationId = reservation.getId();
        if (this.userId == null) this.userId = reservation.getUserId();
        if (this.vehicleId == null) this.vehicleId = reservation.getVehicleId();
        return true;
    }
    
    /**
     * Variante que enlaza usuario y vehículo explícitamente.
     */
    public void linkUserAndVehicle(User user, Vehicle vehicle) {
        if (user != null) this.userId = user.getId();
        if (vehicle != null) this.vehicleId = vehicle.getId();
    }
    
    public void assignToTechnician(String technicianId) {
        this.resolvedBy = technicianId;
        this.status = IncidentStatus.IN_PROGRESS;
    }
    
    public void resolve(String resolution) {
        this.resolution = resolution;
        this.resolvedAt = LocalDateTime.now();
        this.status = IncidentStatus.RESOLVED;
    }
    
    public void close() {
        this.status = IncidentStatus.CLOSED;
    }
    
    public boolean isOpen() {
        return status == IncidentStatus.REPORTED || status == IncidentStatus.IN_PROGRESS;
    }
    
    public boolean isCritical() {
        return severity == IncidentSeverity.CRITICAL || severity == IncidentSeverity.HIGH;
    }
    
    public long getResolutionTimeInMinutes() {
        if (resolvedAt != null) {
            return java.time.Duration.between(reportedAt, resolvedAt).toMinutes();
        }
        return 0;
    }
}
