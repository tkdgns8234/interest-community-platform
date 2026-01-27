package com.findy.post.domain.event;

import com.findy.common.event.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreatedEvent extends BaseDomainEvent {
    private Long commentId;
    private Long postId;
    private Long commentAuthorId;
    private Long postAuthorId;
    private String commentContent;

    @Override
    public Long getAggregateId() {
        return commentId;
    }
}
