package com.findy.boundedcontext.notification.app.usecase;

import com.findy.boundedcontext.notification.app.interfaces.NotificationRepository;
import com.findy.boundedcontext.notification.app.interfaces.NotificationSender;
import com.findy.boundedcontext.notification.domain.model.Notification;
import com.findy.boundedcontext.notification.domain.model.NotificationInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateNotificationUseCase {
    private final NotificationRepository notificationRepository;
    private final NotificationSender notificationSender;

    @Transactional
    public Notification execute(Long recipientId, NotificationInfo info) {
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
