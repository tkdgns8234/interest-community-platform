package com.findy.topic.app.exception;

import com.findy.common.exception.ApplicationException;

public class UnauthorizedTopicPostAccessException extends ApplicationException {
    private static final String CODE = "TOPIC_POST_UNAUTHORIZED_ACCESS";
    private static final int STATUS = 403;

    public UnauthorizedTopicPostAccessException() {
        super(CODE, "Unauthorized access to topic post", STATUS);
    }

    public UnauthorizedTopicPostAccessException(String message) {
        super(CODE, message, STATUS);
    }
}
