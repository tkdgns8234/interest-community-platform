package com.findy.post.domain.event;

import com.findy.common.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreatedEvent implements DomainEvent {
    private Long commentId;
    private Long postId;
    private Long commentAuthorId;
    private Long postAuthorId;
    private String commentContent;
    private LocalDateTime occurredAt;

    @Override
    public Long getAggregateId() {
        return commentId;
    }

    @Override
    public String getEventType() {
        return "COMMENT_CREATED";
    }
}
