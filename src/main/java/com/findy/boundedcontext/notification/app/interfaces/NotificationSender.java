package com.findy.boundedcontext.notification.app.interfaces;

import com.findy.boundedcontext.notification.domain.model.Notification;

public interface NotificationSender {
    void send(Notification notification);
}
