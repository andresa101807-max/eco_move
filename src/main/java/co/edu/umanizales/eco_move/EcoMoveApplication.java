package co.edu.umanizales.eco_move;

import co.edu.umanizales.eco_move.model.*;
import co.edu.umanizales.eco_move.model.enums.UserType;
import co.edu.umanizales.eco_move.model.records.Coordinates;
import co.edu.umanizales.eco_move.service.RouteService;
import co.edu.umanizales.eco_move.service.UserService;
import co.edu.umanizales.eco_move.service.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcoMoveApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoMoveApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(UserService userService, VehicleService vehicleService, RouteService routeService) {
        return args -> {
            // Seed a default user if none exists
            if (userService.findAll().isEmpty()) {
                User user = new User(
                        "Demo User",
                        "demo@example.com",
                        "3000000000",
                        java.time.LocalDate.of(1990, 1, 1),
                        "DOC-0001",
                        UserType.REGULAR
                );
                userService.create(user);
            }

            // Seed a few vehicles if none exists
            if (vehicleService.findAll().isEmpty()) {
                ElectricBike bike = new ElectricBike(
                        "Seed Bike", "SB-1", "EcoBrand", 4.5,
                        6, true, 18.0
                );
                vehicleService.create(bike);

                ElectricScooter scooter = new ElectricScooter(
                        "Seed Scooter", "SS-1", "EcoBrand", 3.0,
                        true, 100.0
                );
                vehicleService.create(scooter);

                HybridCar car = new HybridCar(
                        "Seed Car", "SC-1", "EcoBrand", 12.0,
                        4, 45.0, true
                );
                vehicleService.create(car);

                DeliveryDrone drone = new DeliveryDrone(
                        "Seed Drone", "SD-1", "EcoBrand", 20.0,
                        2.0, 120.0
                );
                vehicleService.create(drone);
            }

            // Seed a default route if none exists
            if (routeService.findAll().isEmpty()) {
                Route route = new Route(
                        "Seed Route",
                        "Ruta de demostración",
                        new Coordinates(5.0700, -75.5140),
                        new Coordinates(5.0720, -75.5120),
                        "easy"
                );
                routeService.create(route);
            }
        };
    }
}
