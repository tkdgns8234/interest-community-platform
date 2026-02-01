package com.findy.boundedcontext.topic.domain.exception;

import com.findy.global.exception.DomainException;

public class CreatorCannotLeaveTopicException extends DomainException {
    private static final String CODE = "TOPIC_CREATOR_CANNOT_LEAVE";

    public CreatorCannotLeaveTopicException() {
        super(CODE, "Topic creator cannot leave the topic");
    }

    public CreatorCannotLeaveTopicException(String message) {
        super(CODE, message);
    }
}
