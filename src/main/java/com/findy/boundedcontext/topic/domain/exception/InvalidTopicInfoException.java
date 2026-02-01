package com.findy.boundedcontext.topic.domain.exception;

import com.findy.global.exception.DomainException;

public class InvalidTopicInfoException extends DomainException {
    private static final String CODE = "TOPIC_INVALID_INFO";

    public InvalidTopicInfoException(String message) {
        super(CODE, message);
    }
}
