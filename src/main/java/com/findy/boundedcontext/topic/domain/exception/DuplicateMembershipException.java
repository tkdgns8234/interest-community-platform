package com.findy.boundedcontext.topic.domain.exception;

import com.findy.global.exception.DomainException;

public class DuplicateMembershipException extends DomainException {
    private static final String CODE = "TOPIC_DUPLICATE_MEMBERSHIP";

    public DuplicateMembershipException(String message) {
        super(CODE, message);
    }
}