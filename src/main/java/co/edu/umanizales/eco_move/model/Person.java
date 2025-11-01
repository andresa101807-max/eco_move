package co.edu.umanizales.eco_move.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Abstract base class for all person entities in the system
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Person {
    protected String id;
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected LocalDate dateOfBirth;
    protected String documentNumber;
    
    public abstract String getRole();
    
    public abstract boolean canAccessSystem();
    
    public int getAge() {
        if (dateOfBirth != null) {
            return LocalDate.now().getYear() - dateOfBirth.getYear();
        }
        return 0;
    }
    
    public boolean isAdult() {
        return getAge() >= 18;
    }
    
    public String getFullContactInfo() {
        return String.format("%s - %s - %s", name, email, phoneNumber);
    }
}
