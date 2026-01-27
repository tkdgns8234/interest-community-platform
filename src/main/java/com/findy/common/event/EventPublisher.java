package com.findy.common.event;

public interface EventPublisher {
    void publish(DomainEvent event);
}