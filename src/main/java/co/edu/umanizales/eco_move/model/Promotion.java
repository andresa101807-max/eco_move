package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.PromotionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {
    private String id;
    private String code;
    private String name;
    private String description;
    private PromotionType type;
    private double discountValue;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxUses;
    private int currentUses;
    private double minPurchaseAmount;
    private boolean isActive;
    
    public Promotion(String code, String name, PromotionType type, double discountValue, LocalDate endDate) {
        this.id = UUID.randomUUID().toString();
        this.code = code.toUpperCase();
        this.name = name;
        this.type = type;
        this.discountValue = discountValue;
        this.startDate = LocalDate.now();
        this.endDate = endDate;
        this.maxUses = 100;
        this.currentUses = 0;
        this.minPurchaseAmount = 0.0;
        this.isActive = true;
    }
    
    public boolean isValid() {
        LocalDate today = LocalDate.now();
        return isActive 
            && !today.isBefore(startDate) 
            && !today.isAfter(endDate)
            && (maxUses == -1 || currentUses < maxUses);
    }
    
    public double calculateDiscount(double amount) {
        if (!isValid() || amount < minPurchaseAmount) {
            return 0.0;
        }
        
        if (type == PromotionType.PERCENTAGE) {
            return amount * (discountValue / 100.0);
        } else {
            return Math.min(discountValue, amount);
        }
    }
    
    public void use() {
        if (isValid()) {
            currentUses++;
        }
    }
    
    public void deactivate() {
        this.isActive = false;
    }
    
    public int getRemainingUses() {
        if (maxUses == -1) {
            return Integer.MAX_VALUE;
        }
        return Math.max(0, maxUses - currentUses);
    }
}
