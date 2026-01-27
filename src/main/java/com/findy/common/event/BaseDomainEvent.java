package com.findy.common.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseDomainEvent {
    private final LocalDateTime occurredAt;

    protected BaseDomainEvent() {
        this.occurredAt = LocalDateTime.now();
    }

    protected BaseDomainEvent(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    public abstract Long getAggregateId();

    public String getEventType() {
        return this.getClass().getSimpleName();
    }
}