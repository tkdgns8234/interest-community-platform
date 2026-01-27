package com.findy.notification.in.event;

import com.findy.notification.app.NotificationService;
import com.findy.notification.domain.model.NotificationInfo;
import com.findy.notification.domain.model.NotificationType;
import com.findy.notification.out.interfaces.UserRelationEntryPoint;
import com.findy.post.domain.event.PostCreatedEvent;
import com.findy.user.app.interfaces.UserRelationRepository;
import com.findy.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostNotificationHandler {
    private final NotificationService notificationService;
    private final UserRelationEntryPoint userRelationEntryPoint;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePostCreated(PostCreatedEvent event) {
        log.info("Handling PostCreatedEvent: postId={}, authorId={}",
            event.getPostId(), event.getAuthorId());

        // 팔로워 조회 (페이지네이션)
        Long cursor = null;
        int pageSize = 100;
        int totalNotifications = 0;

        while (true) {
            List<Long> followerIds = userRelationEntryPoint.findFollowers(
                event.getAuthorId(),
                cursor,
                pageSize
            );

            if (followerIds == null || followerIds.isEmpty()) {
                return;
            }

            // 각 팔로워에게 알림 생성
            for (Long id : followerIds) {
                NotificationInfo info = new NotificationInfo(
                    NotificationType.POST_CREATED,
                    "새 게시글",
                    String.format("팔로우하는 회원 %d님이 새 게시글을 작성했습니다: %s",
                        event.getAuthorId(),
                        truncateTitle(event.getTitle())
                    )
                );

                notificationService.createNotification(id, info);
                totalNotifications++;
            }

            // 다음 페이지 조회를 위한 cursor 설정
            cursor = followerIds.get(followerIds.size() - 1);

            // 마지막 페이지인지 확인
            if (followerIds.size() < pageSize) {
                break;
            }
        }

        log.info("Created {} notifications for post {}", totalNotifications, event.getPostId());
    }

    private String truncateTitle(String title) {
        if (title == null) return "";
        return title.length() > 30 ? title.substring(0, 30) + "..." : title;
    }
}
