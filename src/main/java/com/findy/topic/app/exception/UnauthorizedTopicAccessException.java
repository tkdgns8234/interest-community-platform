package com.findy.topic.app.exception;

import com.findy.common.exception.ApplicationException;

public class UnauthorizedTopicAccessException extends ApplicationException {
    private static final String CODE = "TOPIC_UNAUTHORIZED";
    private static final int STATUS = 401;

    public UnauthorizedTopicAccessException() {
        super(CODE, "You are not authorized to modify this topic", STATUS);
    }
}
