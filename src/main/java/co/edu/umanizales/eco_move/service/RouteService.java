package co.edu.umanizales.eco_move.service;

import co.edu.umanizales.eco_move.model.Route;
import co.edu.umanizales.eco_move.repository.RouteCsvRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RouteService {
    private final RouteCsvRepository routeRepository;

    public RouteService(RouteCsvRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Optional<Route> findById(String id) {
        return routeRepository.findById(id);
    }

    public Route create(Route route) {
        if (route.getId() == null || route.getId().isEmpty()) {
            route.setId(UUID.randomUUID().toString());
        }
        return routeRepository.save(route);
    }

    public Route update(Route route) {
        return routeRepository.save(route);
    }

    public void delete(String id) {
        routeRepository.deleteById(id);
    }
}
