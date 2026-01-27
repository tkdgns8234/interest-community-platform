package com.findy.common.event;

import java.time.LocalDateTime;

public interface DomainEvent {
    Long getAggregateId();
    LocalDateTime getOccurredAt();
    String getEventType();
}