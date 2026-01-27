package com.findy.notification.domain.model;

import lombok.Getter;

@Getter
public class NotificationInfo {
    private final NotificationType type;
    private final String title;
    private final String message;

    public NotificationInfo(NotificationType type, String title, String message) {
        this.type = type;
        this.title = title;
        this.message = message;
    }
}
