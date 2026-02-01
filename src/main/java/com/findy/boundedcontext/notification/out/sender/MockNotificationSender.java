package com.findy.boundedcontext.notification.out.sender;

import com.findy.boundedcontext.notification.app.interfaces.NotificationSender;
import com.findy.boundedcontext.notification.domain.model.Notification;
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
