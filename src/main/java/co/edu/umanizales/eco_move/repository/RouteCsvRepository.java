package co.edu.umanizales.eco_move.repository;

import co.edu.umanizales.eco_move.model.Route;
import co.edu.umanizales.eco_move.model.records.Coordinates;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class RouteCsvRepository implements CsvRepository<Route> {
    private static final String FILE_PATH = "data/routes.csv";
    private static final String[] HEADER = {"id", "name", "description", "startPoint", "endPoint", 
                                           "waypoints", "estimatedDistance", "estimatedDuration", "difficulty"};
    
    public RouteCsvRepository() {
        initializeFile();
    }
    
    private void initializeFile() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        
        if (!file.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                writer.writeNext(HEADER);
            } catch (IOException e) {
                throw new RuntimeException("Error initializing routes CSV file", e);
            }
        }
    }
    
    @Override
    public List<Route> findAll() {
        List<Route> routes = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH))) {
            String[] line;
            reader.readNext(); // Skip header
            
            while ((line = reader.readNext()) != null) {
                routes.add(parseRoute(line));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading routes from CSV", e);
        }
        
        return routes;
    }
    
    @Override
    public Optional<Route> findById(String id) {
        return findAll().stream()
                .filter(route -> route.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public Route save(Route route) {
        List<Route> routes = findAll();
        routes.removeIf(r -> r.getId().equals(route.getId()));
        routes.add(route);
        saveAll(routes);
        return route;
    }
    
    @Override
    public void deleteById(String id) {
        List<Route> routes = findAll();
        routes.removeIf(route -> route.getId().equals(id));
        saveAll(routes);
    }
    
    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }
    
    private void saveAll(List<Route> routes) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH))) {
            writer.writeNext(HEADER);
            
            for (Route route : routes) {
                writer.writeNext(toStringArray(route));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing routes to CSV", e);
        }
    }
    
    private Route parseRoute(String[] data) {
        Route route = new Route();
        route.setId(data[0]);
        route.setName(data[1]);
        route.setDescription(data[2]);
        route.setStartPoint(Coordinates.fromString(data[3]));
        route.setEndPoint(Coordinates.fromString(data[4]));
        
        List<Coordinates> waypoints = new ArrayList<>();
        if (!data[5].isEmpty()) {
            String[] waypointStrings = data[5].split("\\|");
            for (String wp : waypointStrings) {
                waypoints.add(Coordinates.fromString(wp));
            }
        }
        route.setWaypoints(waypoints);
        
        route.setEstimatedDistance(Double.parseDouble(data[6]));
        route.setEstimatedDuration(Integer.parseInt(data[7]));
        route.setDifficulty(data[8]);
        
        return route;
    }
    
    private String[] toStringArray(Route route) {
        StringBuilder waypointsStr = new StringBuilder();
        if (route.getWaypoints() != null) {
            for (int i = 0; i < route.getWaypoints().size(); i++) {
                waypointsStr.append(route.getWaypoints().get(i).toString());
                if (i < route.getWaypoints().size() - 1) {
                    waypointsStr.append("|");
                }
            }
        }
        
        return new String[]{
            route.getId(),
            route.getName(),
            route.getDescription(),
            route.getStartPoint().toString(),
            route.getEndPoint().toString(),
            waypointsStr.toString(),
            String.valueOf(route.getEstimatedDistance()),
            String.valueOf(route.getEstimatedDuration()),
            route.getDifficulty()
        };
    }
}
