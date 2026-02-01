package com.findy.boundedcontext.topic.app.exception;

import com.findy.global.exception.ApplicationException;

public class TopicNotFoundException extends ApplicationException {
    private static final String CODE = "TOPIC_NOT_FOUND";
    private static final int STATUS = 404;

    public TopicNotFoundException(Long topicId) {
        super(CODE, "Topic not found with id: " + topicId, STATUS);
    }
}
