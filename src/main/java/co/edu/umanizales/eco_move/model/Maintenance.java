package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.MaintenanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Maintenance {
    private String id;
    private String vehicleId;
    private MaintenanceType type;
    private LocalDateTime scheduledDate;
    private LocalDateTime completedDate;
    private String description;
    private double cost;
    private String technicianName;
    private boolean isCompleted;
    
    public Maintenance(String vehicleId, MaintenanceType type, LocalDateTime scheduledDate,
                      String description, String technicianName) {
        this.id = UUID.randomUUID().toString();
        this.vehicleId = vehicleId;
        this.type = type;
        this.scheduledDate = scheduledDate;
        this.description = description;
        this.technicianName = technicianName;
        this.cost = 0.0;
        this.isCompleted = false;
    }
    
    public void complete(double cost) {
        this.isCompleted = true;
        this.completedDate = LocalDateTime.now();
        this.cost = cost;
    }
    
    public void reschedule(LocalDateTime newDate) {
        if (!isCompleted) {
            this.scheduledDate = newDate;
        }
    }
}
