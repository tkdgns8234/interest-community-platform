package com.findy.topic.app.exception;

import com.findy.common.exception.ApplicationException;

public class MembershipNotFoundException extends ApplicationException {
    private static final String CODE = "TOPIC_MEMBERSHIP_NOT_FOUND";
    private static final int STATUS = 404;

    public MembershipNotFoundException(String message) {
        super(CODE, message, STATUS);
    }

    public MembershipNotFoundException(Long userId, Long topicId) {
        super(CODE, "Membership not found for user: " + userId + " and topic: " + topicId, STATUS);
    }
}
