package com.findy.notification.out.repository;

import com.findy.notification.app.exception.NotificationNotFoundException;
import com.findy.notification.app.interfaces.NotificationRepository;
import com.findy.notification.domain.model.Notification;
import com.findy.notification.out.repository.entity.NotificationEntity;
import com.findy.notification.out.repository.jpa.JpaNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final JpaNotificationRepository jpaNotificationRepository;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = new NotificationEntity(notification);
        NotificationEntity saved = jpaNotificationRepository.save(entity);
        return saved.toNotification();
    }

    @Override
    public Notification findById(Long id) {
        return jpaNotificationRepository.findById(id)
            .map(NotificationEntity::toNotification)
            .orElseThrow(() -> new NotificationNotFoundException("Notification not found: " + id));
    }
}
