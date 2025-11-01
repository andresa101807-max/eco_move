package co.edu.umanizales.eco_move.controller;

import co.edu.umanizales.eco_move.model.Maintenance;
import co.edu.umanizales.eco_move.service.MaintenanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;
    
    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }
    
    @GetMapping
    public ResponseEntity<List<Maintenance>> getAllMaintenances() {
        return ResponseEntity.ok(maintenanceService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenanceById(@PathVariable String id) {
        return maintenanceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Maintenance> createMaintenance(@RequestBody Maintenance maintenance) {
        try {
            Maintenance created = maintenanceService.create(maintenance);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> updateMaintenance(@PathVariable String id, @RequestBody Maintenance maintenance) {
        try {
            Maintenance updated = maintenanceService.update(id, maintenance);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable String id) {
        try {
            maintenanceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/complete")
    public ResponseEntity<Maintenance> completeMaintenance(@PathVariable String id, @RequestParam double cost) {
        try {
            Maintenance completed = maintenanceService.completeMaintenance(id, cost);
            return ResponseEntity.ok(completed);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/reschedule")
    public ResponseEntity<Maintenance> rescheduleMaintenance(
            @PathVariable String id, 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newDate) {
        try {
            Maintenance rescheduled = maintenanceService.rescheduleMaintenance(id, newDate);
            return ResponseEntity.ok(rescheduled);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
