package co.edu.umanizales.eco_move.service;

import co.edu.umanizales.eco_move.model.Route;
import co.edu.umanizales.eco_move.repository.RouteCsvRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final RouteCsvRepository repository;
    
    public RouteService(RouteCsvRepository repository) {
        this.repository = repository;
    }
    
    public List<Route> findAll() {
        return repository.findAll();
    }
    
    public Optional<Route> findById(String id) {
        return repository.findById(id);
    }
    
    public Route create(Route route) {
        return repository.save(route);
    }
    
    public Route update(String id, Route route) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Route not found with id: " + id);
        }
        route.setId(id);
        return repository.save(route);
    }
    
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Route not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
