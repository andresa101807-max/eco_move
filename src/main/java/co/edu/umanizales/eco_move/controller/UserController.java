package co.edu.umanizales.eco_move.controller;

import co.edu.umanizales.eco_move.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final List<User> users = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        for (User u : users) {
            if (u.getId() != null && u.getId().equals(id)) {
                return ResponseEntity.ok(u);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        if (user.getId() == null || user.getId().isBlank()) {
            user.setId(UUID.randomUUID().toString());
        }
        users.add(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody User updated) {
        for (User u : users) {
            if (u.getId() != null && u.getId().equals(id)) {
                // actualizar campos básicos con for-each para mantener el patrón
                u.setName(updated.getName());
                u.setEmail(updated.getEmail());
                u.setPhoneNumber(updated.getPhoneNumber());
                u.setDateOfBirth(updated.getDateOfBirth());
                u.setDocumentNumber(updated.getDocumentNumber());
                u.setUserType(updated.getUserType());
                u.setBalance(updated.getBalance());
                u.setActive(updated.isActive());
                u.setTripHistory(updated.getTripHistory());
                return ResponseEntity.ok(u);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        User toRemove = null;
        for (User u : users) {
            if (u.getId() != null && u.getId().equals(id)) {
                toRemove = u;
                break;
            }
        }
        if (toRemove != null) {
            users.remove(toRemove);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
