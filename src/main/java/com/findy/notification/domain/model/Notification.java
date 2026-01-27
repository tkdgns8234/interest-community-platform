package com.findy.notification.domain.model;

import lombok.Getter;

@Getter
public class Notification {
    private final Long id;
    private final Long recipientId;
    private final NotificationInfo notificationInfo;
    private NotificationStatus status;

    public Notification(Long id, Long recipientId, NotificationInfo info) {
        this(id, recipientId, info, NotificationStatus.PENDING);
    }

    public Notification(Long id, Long recipientId, NotificationInfo info, NotificationStatus status) {
        this.id = id;
        this.recipientId = recipientId;
        this.notificationInfo = info;
        this.status = status;
    }

    public void markAsSent() {
        this.status = NotificationStatus.SENT;
    }

    public void markAsFailed() {
        this.status = NotificationStatus.FAILED;
    }
}
