package com.findy.notification.out.sender;

import com.findy.notification.app.interfaces.NotificationSender;
import com.findy.notification.domain.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockNotificationSender implements NotificationSender {

    @Override
    public void send(Notification notification) {
        log.info("Mock sending notification: [{}] to user {} - {}",
            notification.getNotificationInfo().getType(),
            notification.getRecipientId(),
            notification.getNotificationInfo().getMessage()
        );
    }
}
