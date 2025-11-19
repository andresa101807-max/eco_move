package co.edu.umanizales.eco_move.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import co.edu.umanizales.eco_move.model.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String id;
    private String userId;
    private String vehicleId;
    private String reservationId;
    private int rating; // 1-5 stars
    private String comment;
    private LocalDateTime createdAt;
    private boolean isVerified;
    private int helpfulCount;
    
    public Review(String userId, String vehicleId, String reservationId, int rating, String comment) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.reservationId = reservationId;
        this.rating = Math.max(1, Math.min(5, rating)); // Ensure rating is between 1-5
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.isVerified = false;
        this.helpfulCount = 0;
    }
    
    public void verify() {
        this.isVerified = true;
    }
    
    public void markAsHelpful() {
        this.helpfulCount++;
    }
    
    public boolean isPositive() {
        return rating >= 4;
    }
    
    public boolean isNegative() {
        return rating <= 2;
    }
    
    public boolean isNeutral() {
        return rating == 3;
    }

    /**
     * Enlaza una reserva con validación de consistencia de usuario/vehículo.
     */
    public boolean linkReservation(Reservation reservation) {
        if (reservation == null) {
            return false;
        }
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
     * Verifica la reseña solo si la reserva está COMPLETED y coincide con userId/vehicleId.
     */
    public boolean verifyAgainstReservation(Reservation reservation) {
        if (reservation == null) {
            return false;
        }
        boolean matches = (this.userId != null && this.userId.equals(reservation.getUserId()))
                && (this.vehicleId != null && this.vehicleId.equals(reservation.getVehicleId()));
        if (matches && reservation.getStatus() == ReservationStatus.COMPLETED) {
            this.isVerified = true;
            return true;
        }
        return false;
    }
}

