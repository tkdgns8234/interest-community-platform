package com.findy.topic.domain.exception;

import com.findy.common.exception.DomainException;

public class OnlyTopicMemberCanWriteException extends DomainException {
    private static final String CODE = "TOPIC_ONLY_MEMBER_CAN_WRITE";

    public OnlyTopicMemberCanWriteException() {
        super(CODE, "Only topic members can write posts");
    }

    public OnlyTopicMemberCanWriteException(String message) {
        super(CODE, message);
    }
}
