package co.edu.umanizales.eco_move.repository;

import co.edu.umanizales.eco_move.model.*;
import co.edu.umanizales.eco_move.model.enums.VehicleStatus;
import co.edu.umanizales.eco_move.model.enums.VehicleType;
import co.edu.umanizales.eco_move.model.records.Coordinates;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VehicleCsvRepository implements CsvRepository<Vehicle> {
    private static final String FILE_PATH = "data/vehicles.csv";
    private static final String[] HEADER = {"id", "name", "model", "brand", "type", "status", 
                                           "pricePerHour", "currentLocation", "battery", "specificData"};
    
    public VehicleCsvRepository() {
        initializeFile();
    }
    
    private void initializeFile() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        
        if (!file.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                writer.writeNext(HEADER);
            } catch (IOException e) {
                throw new RuntimeException("Error initializing vehicles CSV file", e);
            }
        }
    }
    
    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH))) {
            String[] line;
            reader.readNext(); // Skip header
            
            while ((line = reader.readNext()) != null) {
                vehicles.add(parseVehicle(line));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading vehicles from CSV", e);
        }
        
        return vehicles;
    }
    
    @Override
    public Optional<Vehicle> findById(String id) {
        return findAll().stream()
                .filter(vehicle -> vehicle.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public Vehicle save(Vehicle vehicle) {
        List<Vehicle> vehicles = findAll();
        vehicles.removeIf(v -> v.getId().equals(vehicle.getId()));
        vehicles.add(vehicle);
        saveAll(vehicles);
        return vehicle;
    }
    
    @Override
    public void deleteById(String id) {
        List<Vehicle> vehicles = findAll();
        vehicles.removeIf(vehicle -> vehicle.getId().equals(id));
        saveAll(vehicles);
    }
    
    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }
    
    private void saveAll(List<Vehicle> vehicles) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH))) {
            writer.writeNext(HEADER);
            
            for (Vehicle vehicle : vehicles) {
                writer.writeNext(toStringArray(vehicle));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing vehicles to CSV", e);
        }
    }
    
    private Vehicle parseVehicle(String[] data) {
        VehicleType type = VehicleType.valueOf(data[4]);
        Vehicle vehicle;
        
        switch (type) {
            case ELECTRIC_BIKE:
                vehicle = parseElectricBike(data);
                break;
            case ELECTRIC_SCOOTER:
                vehicle = parseElectricScooter(data);
                break;
            case HYBRID_CAR:
                vehicle = parseHybridCar(data);
                break;
            case DELIVERY_DRONE:
                vehicle = parseDeliveryDrone(data);
                break;
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
        
        vehicle.setId(data[0]);
        vehicle.setName(data[1]);
        vehicle.setModel(data[2]);
        vehicle.setBrand(data[3]);
        vehicle.setType(type);
        vehicle.setStatus(VehicleStatus.valueOf(data[5]));
        vehicle.setPricePerHour(Double.parseDouble(data[6]));
        vehicle.setCurrentLocation(Coordinates.fromString(data[7]));
        vehicle.setBattery(Battery.fromString(data[8]));
        
        return vehicle;
    }
    
    private ElectricBike parseElectricBike(String[] data) {
        String[] specific = data[9].split(";");
        ElectricBike bike = new ElectricBike();
        bike.setGears(Integer.parseInt(specific[0]));
        bike.setHasLights(Boolean.parseBoolean(specific[1]));
        bike.setWeight(Double.parseDouble(specific[2]));
        return bike;
    }
    
    private ElectricScooter parseElectricScooter(String[] data) {
        String[] specific = data[9].split(";");
        ElectricScooter scooter = new ElectricScooter();
        scooter.setHasSuspension(Boolean.parseBoolean(specific[0]));
        scooter.setMaxLoad(Double.parseDouble(specific[1]));
        return scooter;
    }
    
    private HybridCar parseHybridCar(String[] data) {
        String[] specific = data[9].split(";");
        HybridCar car = new HybridCar();
        car.setSeats(Integer.parseInt(specific[0]));
        car.setFuelCapacity(Double.parseDouble(specific[1]));
        car.setCurrentFuel(Double.parseDouble(specific[2]));
        car.setHasAirConditioning(Boolean.parseBoolean(specific[3]));
        return car;
    }
    
    private DeliveryDrone parseDeliveryDrone(String[] data) {
        String[] specific = data[9].split(";");
        DeliveryDrone drone = new DeliveryDrone();
        drone.setMaxPayload(Double.parseDouble(specific[0]));
        drone.setMaxAltitude(Double.parseDouble(specific[1]));
        drone.setAutoPilotEnabled(Boolean.parseBoolean(specific[2]));
        return drone;
    }
    
    private String[] toStringArray(Vehicle vehicle) {
        String specificData = getSpecificData(vehicle);
        
        return new String[]{
            vehicle.getId(),
            vehicle.getName(),
            vehicle.getModel(),
            vehicle.getBrand(),
            vehicle.getType().toString(),
            vehicle.getStatus().toString(),
            String.valueOf(vehicle.getPricePerHour()),
            vehicle.getCurrentLocation().toString(),
            vehicle.getBattery().toString(),
            specificData
        };
    }
    
    private String getSpecificData(Vehicle vehicle) {
        if (vehicle instanceof ElectricBike bike) {
            return bike.getGears() + ";" + bike.isHasLights() + ";" + bike.getWeight();
        } else if (vehicle instanceof ElectricScooter scooter) {
            return scooter.isHasSuspension() + ";" + scooter.getMaxLoad();
        } else if (vehicle instanceof HybridCar car) {
            return car.getSeats() + ";" + car.getFuelCapacity() + ";" + 
                   car.getCurrentFuel() + ";" + car.isHasAirConditioning();
        } else if (vehicle instanceof DeliveryDrone drone) {
            return drone.getMaxPayload() + ";" + drone.getMaxAltitude() + ";" + 
                   drone.isAutoPilotEnabled();
        }
        return "";
    }
}
