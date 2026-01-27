package com.findy.notification.app;

import com.findy.notification.app.interfaces.NotificationRepository;
import com.findy.notification.app.interfaces.NotificationSender;
import com.findy.notification.domain.model.Notification;
import com.findy.notification.domain.model.NotificationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationSender notificationSender;

    @Transactional
    public Notification createNotification(Long recipientId, NotificationInfo info) {
        Notification notification = new Notification(null, recipientId, info);
        notification = notificationRepository.save(notification);

        try {
            notificationSender.send(notification);
            notification.markAsSent();
        } catch (Exception e) {
            log.error("Failed to send notification", e);
            notification.markAsFailed();
        }

        return notificationRepository.save(notification);
    }
}
