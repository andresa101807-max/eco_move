package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String documentNumber;
    private UserType userType;
    private double balance;
    private boolean isActive;
    private TripHistory tripHistory;
    
    public User(String name, String email, String phoneNumber, LocalDate dateOfBirth,
               String documentNumber, UserType userType) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.documentNumber = documentNumber;
        this.userType = userType;
        this.balance = 0.0;
        this.isActive = true;
        this.tripHistory = new TripHistory();
    }
    
    public void addBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }
    
    public boolean deductBalance(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
    
    public void addTrip(TripHistory.Trip trip) {
        tripHistory.addTrip(trip);
    }
}
