package co.edu.umanizales.eco_move.service;

import co.edu.umanizales.eco_move.model.Vehicle;
import co.edu.umanizales.eco_move.repository.VehicleCsvRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {
    private final VehicleCsvRepository vehicleRepository;

    public VehicleService(VehicleCsvRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> findById(String id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle create(Vehicle vehicle) {
        if (vehicle.getId() == null || vehicle.getId().isEmpty()) {
            vehicle.setId(UUID.randomUUID().toString());
        }
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void delete(String id) {
        vehicleRepository.deleteById(id);
    }
}
