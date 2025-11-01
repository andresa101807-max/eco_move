package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.EmployeeRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Concrete class extending Person - represents system employees
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {
    private EmployeeRole employeeRole;
    private String department;
    private LocalDate hireDate;
    private double salary;
    private boolean isActive;
    private String supervisorId;
    
    public Employee(String name, String email, String phoneNumber, LocalDate dateOfBirth, 
                   String documentNumber, EmployeeRole employeeRole, String department) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.documentNumber = documentNumber;
        this.employeeRole = employeeRole;
        this.department = department;
        this.hireDate = LocalDate.now();
        this.isActive = true;
        configureSalary(employeeRole);
    }
    
    public Employee() {
        super();
    }
    
    private void configureSalary(EmployeeRole role) {
        switch (role) {
            case TECHNICIAN:
                this.salary = 2500.0;
                break;
            case SUPPORT_AGENT:
                this.salary = 2000.0;
                break;
            case MANAGER:
                this.salary = 4500.0;
                break;
            case ADMINISTRATOR:
                this.salary = 5500.0;
                break;
            default:
                this.salary = 2000.0;
        }
    }
    
    @Override
    public String getRole() {
        return employeeRole != null ? employeeRole.toString() : "EMPLOYEE";
    }
    
    @Override
    public boolean canAccessSystem() {
        return isActive;
    }
    
    public int getYearsOfService() {
        if (hireDate != null) {
            return LocalDate.now().getYear() - hireDate.getYear();
        }
        return 0;
    }
    
    public void promote(EmployeeRole newRole) {
        this.employeeRole = newRole;
        configureSalary(newRole);
    }
    
    public void terminate() {
        this.isActive = false;
    }
}
