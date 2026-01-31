package com.findy.topic.domain.event;

import com.findy.common.event.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicDeletedEvent extends BaseDomainEvent {
    private Long topicId;
    private Long creatorId;

    @Override
    public Long getAggregateId() {
        return topicId;
    }
}
