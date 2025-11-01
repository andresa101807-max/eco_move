package co.edu.umanizales.eco_move.repository;

import co.edu.umanizales.eco_move.model.TripHistory;
import co.edu.umanizales.eco_move.model.User;
import co.edu.umanizales.eco_move.model.enums.UserType;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserCsvRepository implements CsvRepository<User> {
    private static final String FILE_PATH = "data/users.csv";
    private static final String[] HEADER = {"id", "name", "email", "phoneNumber", "dateOfBirth", 
                                           "documentNumber", "userType", "balance", "isActive", "tripHistory"};
    
    public UserCsvRepository() {
        initializeFile();
    }
    
    private void initializeFile() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        
        if (!file.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                writer.writeNext(HEADER);
            } catch (IOException e) {
                throw new RuntimeException("Error initializing users CSV file", e);
            }
        }
    }
    
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(FILE_PATH))) {
            String[] line;
            reader.readNext(); // Skip header
            
            while ((line = reader.readNext()) != null) {
                users.add(parseUser(line));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading users from CSV", e);
        }
        
        return users;
    }
    
    @Override
    public Optional<User> findById(String id) {
        return findAll().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
    
    @Override
    public User save(User user) {
        List<User> users = findAll();
        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);
        saveAll(users);
        return user;
    }
    
    @Override
    public void deleteById(String id) {
        List<User> users = findAll();
        users.removeIf(user -> user.getId().equals(id));
        saveAll(users);
    }
    
    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }
    
    private void saveAll(List<User> users) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE_PATH))) {
            writer.writeNext(HEADER);
            
            for (User user : users) {
                writer.writeNext(toStringArray(user));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing users to CSV", e);
        }
    }
    
    private User parseUser(String[] data) {
        User user = new User();
        user.setId(data[0]);
        user.setName(data[1]);
        user.setEmail(data[2]);
        user.setPhoneNumber(data[3]);
        user.setDateOfBirth(LocalDate.parse(data[4]));
        user.setDocumentNumber(data[5]);
        user.setUserType(UserType.valueOf(data[6]));
        user.setBalance(Double.parseDouble(data[7]));
        user.setActive(Boolean.parseBoolean(data[8]));
        user.setTripHistory(data[9].isEmpty() ? new TripHistory() : TripHistory.fromString(data[9]));
        return user;
    }
    
    private String[] toStringArray(User user) {
        return new String[]{
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getDateOfBirth().toString(),
            user.getDocumentNumber(),
            user.getUserType().toString(),
            String.valueOf(user.getBalance()),
            String.valueOf(user.isActive()),
            user.getTripHistory() != null ? user.getTripHistory().toString() : ""
        };
    }
}
