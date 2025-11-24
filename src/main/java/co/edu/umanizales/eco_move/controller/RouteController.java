package co.edu.umanizales.eco_move.controller;

import co.edu.umanizales.eco_move.model.Route;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final List<Route> routes = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Route>> findAll() {
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> findById(@PathVariable String id) {
        for (Route r : routes) {
            if (r.getId() != null && r.getId().equals(id)) {
                return ResponseEntity.ok(r);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Route> create(@RequestBody Route route) {
        if (route.getId() == null || route.getId().isBlank()) {
            route.setId(UUID.randomUUID().toString());
        }
        routes.add(route);
        return ResponseEntity.ok(route);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Route> update(@PathVariable String id, @RequestBody Route updated) {
        for (Route r : routes) {
            if (r.getId() != null && r.getId().equals(id)) {
                r.setName(updated.getName());
                r.setDescription(updated.getDescription());
                r.setStartPoint(updated.getStartPoint());
                r.setEndPoint(updated.getEndPoint());
                r.setWaypoints(updated.getWaypoints());
                r.setEstimatedDistance(updated.getEstimatedDistance());
                r.setEstimatedDuration(updated.getEstimatedDuration());
                r.setDifficulty(updated.getDifficulty());
                return ResponseEntity.ok(r);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Route toRemove = null;
        for (Route r : routes) {
            if (r.getId() != null && r.getId().equals(id)) {
                toRemove = r;
                break;
            }
        }
        if (toRemove != null) {
            routes.remove(toRemove);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
