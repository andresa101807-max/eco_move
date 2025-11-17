package co.edu.umanizales.eco_move.controller;

import co.edu.umanizales.eco_move.model.Route;
import co.edu.umanizales.eco_move.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {
    private final RouteService routeService;
    
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }
    
    @GetMapping
    public ResponseEntity<List<Route>> getAllRoutes() {
        return ResponseEntity.ok(routeService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable String id) {
        return routeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        if (!isValid(route)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Route created = routeService.create(route);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable String id, @RequestBody Route route) {
        // Si la ruta no existe, 404
        if (routeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Validación simple de datos
        if (!isValid(route)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Route updated = routeService.update(id, route);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable String id) {
        if (routeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Validación muy básica: nombre y puntos de inicio/fin obligatorios
    private boolean isValid(Route route) {
        if (route == null) return false;
        if (route.getName() == null || route.getName().isBlank()) return false;
        if (route.getStartPoint() == null) return false;
        if (route.getEndPoint() == null) return false;
        return true;
    }
}
