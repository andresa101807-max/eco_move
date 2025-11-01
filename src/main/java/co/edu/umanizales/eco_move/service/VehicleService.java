package co.edu.umanizales.eco_move.service;

import co.edu.umanizales.eco_move.model.Vehicle;
import co.edu.umanizales.eco_move.model.enums.VehicleStatus;
import co.edu.umanizales.eco_move.repository.VehicleCsvRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleCsvRepository repository;
    
    public VehicleService(VehicleCsvRepository repository) {
        this.repository = repository;
    }
    
    public List<Vehicle> findAll() {
        return repository.findAll();
    }
    
    public Optional<Vehicle> findById(String id) {
        return repository.findById(id);
    }
    
    public Vehicle create(Vehicle vehicle) {
        return repository.save(vehicle);
    }
    
    public Vehicle update(String id, Vehicle vehicle) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
        vehicle.setId(id);
        return repository.save(vehicle);
    }
    
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
        repository.deleteById(id);
    }
    
    public List<Vehicle> findAvailableVehicles() {
        return repository.findAll().stream()
                .filter(Vehicle::isAvailable)
                .collect(Collectors.toList());
    }
    
    public Vehicle updateStatus(String id, VehicleStatus status) {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        vehicle.setStatus(status);
        return repository.save(vehicle);
    }
    
    public Vehicle sendToMaintenance(String id) {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        vehicle.sendToMaintenance();
        return repository.save(vehicle);
    }
}
