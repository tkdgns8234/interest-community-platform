package com.findy.shared.post.event;

import com.findy.global.event.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreatedEvent extends BaseDomainEvent {
    private Long postId;
    private Long authorId;
    private String title;

    @Override
    public Long getAggregateId() {
        return postId;
    }
}
