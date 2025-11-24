package co.edu.umanizales.eco_move.controller;

import co.edu.umanizales.eco_move.model.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final List<Vehicle> vehicles = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Vehicle>> findAll() {
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable String id) {
        for (Vehicle v : vehicles) {
            if (v.getId() != null && v.getId().equals(id)) {
                return ResponseEntity.ok(v);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        if (vehicle.getId() == null || vehicle.getId().isBlank()) {
            vehicle.setId(UUID.randomUUID().toString());
        }
        vehicles.add(vehicle);
        return ResponseEntity.ok(vehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable String id, @RequestBody Vehicle updated) {
        for (Vehicle v : vehicles) {
            if (v.getId() != null && v.getId().equals(id)) {
                v.setName(updated.getName());
                v.setModel(updated.getModel());
                v.setBrand(updated.getBrand());
                v.setType(updated.getType());
                v.setStatus(updated.getStatus());
                v.setPricePerHour(updated.getPricePerHour());
                v.setCurrentLocation(updated.getCurrentLocation());
                v.setBattery(updated.getBattery());
                return ResponseEntity.ok(v);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Vehicle toRemove = null;
        for (Vehicle v : vehicles) {
            if (v.getId() != null && v.getId().equals(id)) {
                toRemove = v;
                break;
            }
        }
        if (toRemove != null) {
            vehicles.remove(toRemove);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
