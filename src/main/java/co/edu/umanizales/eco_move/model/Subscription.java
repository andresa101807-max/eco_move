package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.SubscriptionPlan;
import co.edu.umanizales.eco_move.model.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private String id;
    private String userId;
    private SubscriptionPlan plan;
    private SubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private double monthlyPrice;
    private int tripsIncluded;
    private int tripsUsed;
    private double discountPercentage;
    private boolean autoRenew;
    
    public Subscription(String userId, SubscriptionPlan plan) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.plan = plan;
        this.status = SubscriptionStatus.ACTIVE;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusMonths(1);
        this.tripsUsed = 0;
        this.autoRenew = true;
        configurePlan(plan);
    }
    
    private void configurePlan(SubscriptionPlan plan) {
        switch (plan) {
            case BASIC:
                this.monthlyPrice = 29.99;
                this.tripsIncluded = 10;
                this.discountPercentage = 5.0;
                break;
            case PREMIUM:
                this.monthlyPrice = 49.99;
                this.tripsIncluded = 30;
                this.discountPercentage = 15.0;
                break;
            case UNLIMITED:
                this.monthlyPrice = 99.99;
                this.tripsIncluded = -1; // Unlimited
                this.discountPercentage = 25.0;
                break;
        }
    }
    
    public boolean hasTripsAvailable() {
        return tripsIncluded == -1 || tripsUsed < tripsIncluded;
    }
    
    public void useTrip() {
        if (tripsIncluded != -1) {
            tripsUsed++;
        }
    }
    
    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE && LocalDate.now().isBefore(endDate);
    }
    
    public void renew() {
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusMonths(1);
        this.tripsUsed = 0;
        this.status = SubscriptionStatus.ACTIVE;
    }
    
    public void cancel() {
        this.status = SubscriptionStatus.CANCELLED;
        this.autoRenew = false;
    }
    
    public void suspend() {
        this.status = SubscriptionStatus.SUSPENDED;
    }
    
    public int getRemainingTrips() {
        if (tripsIncluded == -1) {
            return Integer.MAX_VALUE;
        }
        return Math.max(0, tripsIncluded - tripsUsed);
    }
}
