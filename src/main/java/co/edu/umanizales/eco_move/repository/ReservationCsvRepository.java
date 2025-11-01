package co.edu.umanizales.eco_move.repository;

import co.edu.umanizales.eco_move.model.Reservation;
import co.edu.umanizales.eco_move.model.enums.ReservationStatus;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationCsvRepository implements CsvRepository<Reservation> {
    private static final String FILE_PATH = "data/reservations.csv";
    private static final String[] HEADER = {"id", "userId", "vehicleId", "reservationTime", "startTime", 
                                           "endTime", "status", "estimatedCost", "actualCost"};
    
    public ReservationCsvRepository() {
        initializeFile();
    }
    
    private void initializeFile() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        
        if (!file.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                writer.writeNext(HEADER);
            } catch (IOException e) {
                throw new RuntimeException("Error initializing reservations CSV file", e);
            }
        }
    }
    
    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH))) {
            String[] line;
            reader.readNext(); // Skip header
            
            while ((line = reader.readNext()) != null) {
                reservations.add(parseReservation(line));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading reservations from CSV", e);
        }
        
        return reservations;
    }
    
    @Override
    public Optional<Reservation> findById(String id) {
        return findAll().stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public Reservation save(Reservation reservation) {
        List<Reservation> reservations = findAll();
        reservations.removeIf(r -> r.getId().equals(reservation.getId()));
        reservations.add(reservation);
        saveAll(reservations);
        return reservation;
    }
    
    @Override
    public void deleteById(String id) {
        List<Reservation> reservations = findAll();
        reservations.removeIf(reservation -> reservation.getId().equals(id));
        saveAll(reservations);
    }
    
    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }
    
    private void saveAll(List<Reservation> reservations) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH))) {
            writer.writeNext(HEADER);
            
            for (Reservation reservation : reservations) {
                writer.writeNext(toStringArray(reservation));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing reservations to CSV", e);
        }
    }
    
    private Reservation parseReservation(String[] data) {
        Reservation reservation = new Reservation();
        reservation.setId(data[0]);
        reservation.setUserId(data[1]);
        reservation.setVehicleId(data[2]);
        reservation.setReservationTime(LocalDateTime.parse(data[3]));
        reservation.setStartTime(LocalDateTime.parse(data[4]));
        reservation.setEndTime(LocalDateTime.parse(data[5]));
        reservation.setStatus(ReservationStatus.valueOf(data[6]));
        reservation.setEstimatedCost(Double.parseDouble(data[7]));
        reservation.setActualCost(Double.parseDouble(data[8]));
        return reservation;
    }
    
    private String[] toStringArray(Reservation reservation) {
        return new String[]{
            reservation.getId(),
            reservation.getUserId(),
            reservation.getVehicleId(),
            reservation.getReservationTime().toString(),
            reservation.getStartTime().toString(),
            reservation.getEndTime().toString(),
            reservation.getStatus().toString(),
            String.valueOf(reservation.getEstimatedCost()),
            String.valueOf(reservation.getActualCost())
        };
    }
}
