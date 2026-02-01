package com.findy.boundedcontext.notification.out.repository.jpa;

import com.findy.boundedcontext.notification.out.repository.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
