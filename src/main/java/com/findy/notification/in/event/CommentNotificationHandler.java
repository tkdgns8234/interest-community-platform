package com.findy.notification.in.event;

import com.findy.notification.app.NotificationService;
import com.findy.notification.domain.model.NotificationInfo;
import com.findy.notification.domain.model.NotificationType;
import com.findy.post.domain.event.CommentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentNotificationHandler {
    private final NotificationService notificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCommentCreated(CommentCreatedEvent event) {
        log.info("Handling CommentCreatedEvent: commentId={}, postAuthorId={}",
            event.getCommentId(), event.getPostAuthorId());

        // 자기 댓글에는 알림 안 보냄
        if (event.getCommentAuthorId().equals(event.getPostAuthorId())) {
            log.info("Skipping notification: comment author is post author");
            return;
        }

        NotificationInfo info = new NotificationInfo(
            NotificationType.COMMENT_CREATED,
            "새 댓글",
            String.format("회원 %d님이 댓글을 남겼습니다: %s",
                event.getCommentAuthorId(),
                truncateContent(event.getCommentContent())
            )
        );

        notificationService.createNotification(event.getPostAuthorId(), info);
    }

    private String truncateContent(String content) {
        if (content == null) return "";
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }
}
