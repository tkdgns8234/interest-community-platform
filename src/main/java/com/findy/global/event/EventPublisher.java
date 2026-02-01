package com.findy.global.event;

public interface EventPublisher {
    void publish(BaseDomainEvent event);
}