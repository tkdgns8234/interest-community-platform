package com.findy.notification.out.repository.entity;

import com.findy.common.out.repository.entity.BaseTimeEntity;
import com.findy.notification.domain.model.Notification;
import com.findy.notification.domain.model.NotificationInfo;
import com.findy.notification.domain.model.NotificationStatus;
import com.findy.notification.domain.model.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long recipientId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    public NotificationEntity(Notification notification) {
        this.id = notification.getId();
        this.recipientId = notification.getRecipientId();
        this.type = notification.getNotificationInfo().getType();
        this.title = notification.getNotificationInfo().getTitle();
        this.message = notification.getNotificationInfo().getMessage();
        this.status = notification.getStatus();
    }

    public Notification toNotification() {
        NotificationInfo info = new NotificationInfo(
            type,
            title,
            message
        );
        return new Notification(id, recipientId, info, status);
    }
}
