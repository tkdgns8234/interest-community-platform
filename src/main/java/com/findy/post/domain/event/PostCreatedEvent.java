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
public class PostCreatedEvent implements DomainEvent {
    private Long postId;
    private Long authorId;
    private String title;
    private LocalDateTime occurredAt;

    @Override
    public Long getAggregateId() {
        return postId;
    }

    @Override
    public String getEventType() {
        return "POST_CREATED";
    }
}
