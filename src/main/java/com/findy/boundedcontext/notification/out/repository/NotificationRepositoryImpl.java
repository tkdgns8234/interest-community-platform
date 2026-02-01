package com.findy.boundedcontext.notification.out.repository;

import com.findy.boundedcontext.notification.app.exception.NotificationNotFoundException;
import com.findy.boundedcontext.notification.app.interfaces.NotificationRepository;
import com.findy.boundedcontext.notification.domain.model.Notification;
import com.findy.boundedcontext.notification.out.repository.entity.NotificationEntity;
import com.findy.boundedcontext.notification.out.repository.jpa.JpaNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
