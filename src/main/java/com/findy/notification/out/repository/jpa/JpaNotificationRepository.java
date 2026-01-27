package com.findy.notification.out.repository.jpa;

import com.findy.notification.out.repository.entity.NotificationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
