package co.edu.umanizales.eco_move.controller;

import co.edu.umanizales.eco_move.service.MaintenanceService;
import co.edu.umanizales.eco_move.service.ReservationService;
import co.edu.umanizales.eco_move.service.RouteService;
import co.edu.umanizales.eco_move.service.UserService;
import co.edu.umanizales.eco_move.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final UserService userService;
    private final VehicleService vehicleService;
    private final RouteService routeService;
    private final ReservationService reservationService;
    private final MaintenanceService maintenanceService;

    public HealthController(UserService userService,
                            VehicleService vehicleService,
                            RouteService routeService,
                            ReservationService reservationService,
                            MaintenanceService maintenanceService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.routeService = routeService;
        this.reservationService = reservationService;
        this.maintenanceService = maintenanceService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("users", userService.findAll().size());
        result.put("vehicles", vehicleService.findAll().size());
        result.put("routes", routeService.findAll().size());
        result.put("reservations", reservationService.findAll().size());
        result.put("maintenances", maintenanceService.findAll().size());
        return ResponseEntity.ok(result);
    }
}
