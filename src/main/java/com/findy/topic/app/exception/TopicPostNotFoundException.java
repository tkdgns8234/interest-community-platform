package com.findy.topic.app.exception;

import com.findy.common.exception.ApplicationException;

public class TopicPostNotFoundException extends ApplicationException {
    private static final String CODE = "TOPIC_POST_NOT_FOUND";
    private static final int STATUS = 404;

    public TopicPostNotFoundException(Long postId) {
        super(CODE, "Topic post not found with id: " + postId, STATUS);
    }

    public TopicPostNotFoundException(String message) {
        super(CODE, message, STATUS);
    }
}
