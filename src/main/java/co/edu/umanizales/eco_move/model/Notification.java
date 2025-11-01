package co.edu.umanizales.eco_move.model;

import co.edu.umanizales.eco_move.model.enums.NotificationPriority;
import co.edu.umanizales.eco_move.model.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String id;
    private String userId;
    private String title;
    private String message;
    private NotificationType type;
    private NotificationPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
    private boolean isRead;
    private String relatedEntityId;
    
    public Notification(String userId, String title, String message, NotificationType type, NotificationPriority priority) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.priority = priority;
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }
    
    public void markAsRead() {
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }
    
    public boolean isUnread() {
        return !isRead;
    }
    
    public boolean isHighPriority() {
        return priority == NotificationPriority.HIGH || priority == NotificationPriority.URGENT;
    }
}
