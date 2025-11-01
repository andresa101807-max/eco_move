package co.edu.umanizales.eco_move.service;

import co.edu.umanizales.eco_move.model.Reservation;
import co.edu.umanizales.eco_move.model.Vehicle;
import co.edu.umanizales.eco_move.repository.ReservationCsvRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationCsvRepository repository;
    private final VehicleService vehicleService;
    
    public ReservationService(ReservationCsvRepository repository, VehicleService vehicleService) {
        this.repository = repository;
        this.vehicleService = vehicleService;
    }
    
    public List<Reservation> findAll() {
        return repository.findAll();
    }
    
    public Optional<Reservation> findById(String id) {
        return repository.findById(id);
    }
    
    public Reservation create(Reservation reservation) {
        // Verify vehicle is available
        Vehicle vehicle = vehicleService.findById(reservation.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        if (!vehicle.isAvailable()) {
            throw new RuntimeException("Vehicle is not available");
        }
        
        vehicle.reserve();
        vehicleService.update(vehicle.getId(), vehicle);
        
        return repository.save(reservation);
    }
    
    public Reservation update(String id, Reservation reservation) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
        reservation.setId(id);
        return repository.save(reservation);
    }
    
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
        repository.deleteById(id);
    }
    
    public Reservation confirmReservation(String id) {
        Reservation reservation = findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.confirm();
        return repository.save(reservation);
    }
    
    public Reservation startReservation(String id) {
        Reservation reservation = findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        Vehicle vehicle = vehicleService.findById(reservation.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        vehicle.startTrip();
        vehicleService.update(vehicle.getId(), vehicle);
        
        reservation.start();
        return repository.save(reservation);
    }
    
    public Reservation completeReservation(String id, double cost) {
        Reservation reservation = findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        Vehicle vehicle = vehicleService.findById(reservation.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        vehicle.endTrip();
        vehicleService.update(vehicle.getId(), vehicle);
        
        reservation.complete(cost);
        return repository.save(reservation);
    }
    
    public Reservation cancelReservation(String id) {
        Reservation reservation = findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        Vehicle vehicle = vehicleService.findById(reservation.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        vehicle.makeAvailable();
        vehicleService.update(vehicle.getId(), vehicle);
        
        reservation.cancel();
        return repository.save(reservation);
    }
}
