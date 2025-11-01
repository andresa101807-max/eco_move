package co.edu.umanizales.eco_move.repository;

import co.edu.umanizales.eco_move.model.Maintenance;
import co.edu.umanizales.eco_move.model.enums.MaintenanceType;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MaintenanceCsvRepository implements CsvRepository<Maintenance> {
    private static final String FILE_PATH = "data/maintenances.csv";
    private static final String[] HEADER = {"id", "vehicleId", "type", "scheduledDate", "completedDate", 
                                           "description", "cost", "technicianName", "isCompleted"};
    
    public MaintenanceCsvRepository() {
        initializeFile();
    }
    
    private void initializeFile() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        
        if (!file.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                writer.writeNext(HEADER);
            } catch (IOException e) {
                throw new RuntimeException("Error initializing maintenances CSV file", e);
            }
        }
    }
    
    @Override
    public List<Maintenance> findAll() {
        List<Maintenance> maintenances = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH))) {
            String[] line;
            reader.readNext(); // Skip header
            
            while ((line = reader.readNext()) != null) {
                maintenances.add(parseMaintenance(line));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading maintenances from CSV", e);
        }
        
        return maintenances;
    }
    
    @Override
    public Optional<Maintenance> findById(String id) {
        return findAll().stream()
                .filter(maintenance -> maintenance.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public Maintenance save(Maintenance maintenance) {
        List<Maintenance> maintenances = findAll();
        maintenances.removeIf(m -> m.getId().equals(maintenance.getId()));
        maintenances.add(maintenance);
        saveAll(maintenances);
        return maintenance;
    }
    
    @Override
    public void deleteById(String id) {
        List<Maintenance> maintenances = findAll();
        maintenances.removeIf(maintenance -> maintenance.getId().equals(id));
        saveAll(maintenances);
    }
    
    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }
    
    private void saveAll(List<Maintenance> maintenances) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH))) {
            writer.writeNext(HEADER);
            
            for (Maintenance maintenance : maintenances) {
                writer.writeNext(toStringArray(maintenance));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing maintenances to CSV", e);
        }
    }
    
    private Maintenance parseMaintenance(String[] data) {
        Maintenance maintenance = new Maintenance();
        maintenance.setId(data[0]);
        maintenance.setVehicleId(data[1]);
        maintenance.setType(MaintenanceType.valueOf(data[2]));
        maintenance.setScheduledDate(LocalDateTime.parse(data[3]));
        maintenance.setCompletedDate(data[4].isEmpty() ? null : LocalDateTime.parse(data[4]));
        maintenance.setDescription(data[5]);
        maintenance.setCost(Double.parseDouble(data[6]));
        maintenance.setTechnicianName(data[7]);
        maintenance.setCompleted(Boolean.parseBoolean(data[8]));
        return maintenance;
    }
    
    private String[] toStringArray(Maintenance maintenance) {
        return new String[]{
            maintenance.getId(),
            maintenance.getVehicleId(),
            maintenance.getType().toString(),
            maintenance.getScheduledDate().toString(),
            maintenance.getCompletedDate() != null ? maintenance.getCompletedDate().toString() : "",
            maintenance.getDescription(),
            String.valueOf(maintenance.getCost()),
            maintenance.getTechnicianName(),
            String.valueOf(maintenance.isCompleted())
        };
    }
}
