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
        // Defensive checks for legacy rows with missing columns
        String id = data.length > 0 ? data[0] : null;
        String name = data.length > 1 ? data[1] : null;
        String email = data.length > 2 ? data[2] : null;
        String phone = data.length > 3 ? data[3] : null;
        String dob = data.length > 4 ? data[4] : null;
        String doc = data.length > 5 ? data[5] : null;
        String type = data.length > 6 ? data[6] : null;
        String balance = data.length > 7 ? data[7] : null;
        String active = data.length > 8 ? data[8] : null;
        String trips = data.length > 9 ? data[9] : "";
        
        if (id != null && !id.isEmpty()) user.setId(id);
        if (name != null) user.setName(name);
        if (email != null) user.setEmail(email);
        if (phone != null) user.setPhoneNumber(phone);
        if (dob != null && !dob.isEmpty()) user.setDateOfBirth(LocalDate.parse(dob));
        if (doc != null) user.setDocumentNumber(doc);
        if (type != null && !type.isEmpty()) user.setUserType(UserType.valueOf(type));
        if (balance != null && !balance.isEmpty()) user.setBalance(Double.parseDouble(balance));
        if (active != null && !active.isEmpty()) user.setActive(Boolean.parseBoolean(active));
        user.setTripHistory(trips != null && !trips.isEmpty() ? TripHistory.fromString(trips) : new TripHistory());
        return user;
    }
    
    private String[] toStringArray(User user) {
        return new String[]{
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : "",
            user.getDocumentNumber(),
            user.getUserType() != null ? user.getUserType().toString() : "",
            String.valueOf(user.getBalance()),
            String.valueOf(user.isActive()),
            user.getTripHistory() != null ? user.getTripHistory().toString() : ""
        };
    }
}
