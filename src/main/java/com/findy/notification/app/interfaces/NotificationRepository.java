package com.findy.notification.app.interfaces;

import com.findy.notification.domain.model.Notification;

import java.util.List;

public interface NotificationRepository {
    Notification save(Notification notification);
    Notification findById(Long id);
}
