package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Person {
    private UserType userType;     // Tipo de usuario (REGULAR, PREMIUM, ...)
    private double balance;        // Dinero disponible
    private boolean isActive;      // Si la cuenta está activa
    private TripHistory tripHistory; // Historial de viajes realizados
    
    /**
     * Constructor práctico para crear un usuario con datos básicos.
     * Genera automáticamente un id, deja la cuenta activa y saldo en 0.
     */
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
    
    /**
     * Agrega saldo a la cuenta si el valor es positivo.
     */
    public void addBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }
    
    /**
     * Intenta descontar saldo. Devuelve true si fue posible (había saldo suficiente).
     */
    public boolean deductBalance(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
    
    /**
     * Registra un viaje en el historial del usuario.
     */
    public void addTrip(TripHistory.Trip trip) {
        tripHistory.addTrip(trip);
    }

    @Override
    public String getRole() {
        return userType != null ? userType.toString() : "USER";
    }

    @Override
    public boolean canAccessSystem() {
        return isActive;
    }
}

