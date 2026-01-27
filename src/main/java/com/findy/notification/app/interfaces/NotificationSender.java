package com.findy.notification.app.interfaces;

import com.findy.notification.domain.model.Notification;

public interface NotificationSender {
    void send(Notification notification);
}
