package com.findy.boundedcontext.notification.app.interfaces;

import com.findy.boundedcontext.notification.domain.model.Notification;

public interface NotificationRepository {
    Notification save(Notification notification);
    Notification findById(Long id);
}
