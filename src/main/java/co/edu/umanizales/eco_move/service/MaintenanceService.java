package co.edu.umanizales.eco_move.service;

import co.edu.umanizales.eco_move.model.Maintenance;
import co.edu.umanizales.eco_move.model.Vehicle;
import co.edu.umanizales.eco_move.repository.MaintenanceCsvRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {
    private final MaintenanceCsvRepository repository;
    private final VehicleService vehicleService;
    
    public MaintenanceService(MaintenanceCsvRepository repository, VehicleService vehicleService) {
        this.repository = repository;
        this.vehicleService = vehicleService;
    }
    
    public List<Maintenance> findAll() {
        return repository.findAll();
    }
    
    public Optional<Maintenance> findById(String id) {
        return repository.findById(id);
    }
    
    public Maintenance create(Maintenance maintenance) {
        // Send vehicle to maintenance
        Vehicle vehicle = vehicleService.findById(maintenance.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        vehicle.sendToMaintenance();
        vehicleService.update(vehicle.getId(), vehicle);
        
        return repository.save(maintenance);
    }
    
    public Maintenance update(String id, Maintenance maintenance) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Maintenance not found with id: " + id);
        }
        maintenance.setId(id);
        return repository.save(maintenance);
    }
    
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Maintenance not found with id: " + id);
        }
        repository.deleteById(id);
    }
    
    public Maintenance completeMaintenance(String id, double cost) {
        Maintenance maintenance = findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));
        
        maintenance.complete(cost);
        
        // Make vehicle available again
        Vehicle vehicle = vehicleService.findById(maintenance.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        vehicle.makeAvailable();
        vehicleService.update(vehicle.getId(), vehicle);
        
        return repository.save(maintenance);
    }
    
    public Maintenance rescheduleMaintenance(String id, LocalDateTime newDate) {
        Maintenance maintenance = findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));
        
        maintenance.reschedule(newDate);
        return repository.save(maintenance);
    }
}
