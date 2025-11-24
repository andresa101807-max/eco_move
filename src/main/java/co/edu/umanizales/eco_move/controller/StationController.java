package co.edu.umanizales.eco_move.controller;

import co.edu.umanizales.eco_move.model.Station;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final List<Station> stations = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Station>> findAll() {
        return ResponseEntity.ok(stations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Station> findById(@PathVariable String id) {
        for (Station s : stations) {
            if (s.getId() != null && s.getId().equals(id)) {
                return ResponseEntity.ok(s);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Station> create(@RequestBody Station station) {
        if (station.getId() == null || station.getId().isBlank()) {
            station.setId(UUID.randomUUID().toString());
        }
        stations.add(station);
        return ResponseEntity.ok(station);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Station> update(@PathVariable String id, @RequestBody Station updated) {
        for (Station s : stations) {
            if (s.getId() != null && s.getId().equals(id)) {
                s.setName(updated.getName());
                s.setLocation(updated.getLocation());
                s.setType(updated.getType());
                s.setCapacity(updated.getCapacity());
                s.setAvailableSpots(updated.getAvailableSpots());
                s.setVehicleIds(updated.getVehicleIds());
                s.setHasChargingStations(updated.isHasChargingStations());
                s.setOperatingHours(updated.getOperatingHours());
                s.setActive(updated.isActive());
                return ResponseEntity.ok(s);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Station toRemove = null;
        for (Station s : stations) {
            if (s.getId() != null && s.getId().equals(id)) {
                toRemove = s;
                break;
            }
        }
        if (toRemove != null) {
            stations.remove(toRemove);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
