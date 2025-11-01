package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    public void complete(double cost) {
        if (status == ReservationStatus.IN_PROGRESS) {
            status = ReservationStatus.COMPLETED;
            this.actualCost = cost;
        }
    }
    
    public void cancel() {
        if (status != ReservationStatus.COMPLETED) {
            status = ReservationStatus.CANCELLED;
        }
    }
}
