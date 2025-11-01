package co.edu.umanizales.eco_move.service;

import co.edu.umanizales.eco_move.model.User;
import co.edu.umanizales.eco_move.repository.UserCsvRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserCsvRepository repository;
    
    public UserService(UserCsvRepository repository) {
        this.repository = repository;
    }
    
    public List<User> findAll() {
        return repository.findAll();
    }
    
    public Optional<User> findById(String id) {
        return repository.findById(id);
    }
    
    public User create(User user) {
        return repository.save(user);
    }
    
    public User update(String id, User user) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        user.setId(id);
        return repository.save(user);
    }
    
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        repository.deleteById(id);
    }
    
    public User addBalance(String id, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        User user = findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.addBalance(amount);
        return repository.save(user);
    }
    
    public User deductBalance(String id, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        User user = findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (!user.deductBalance(amount)) {
            throw new RuntimeException("Insufficient balance");
        }
        return repository.save(user);
    }
}
