package com.findy.boundedcontext.topic.domain.exception;

import com.findy.global.exception.DomainException;

public class CreatorRoleCannotBeChangedException extends DomainException {
    private static final String CODE = "TOPIC_CREATOR_ROLE_CANNOT_BE_CHANGED";

    public CreatorRoleCannotBeChangedException() {
        super(CODE, "Creator role cannot be changed");
    }
}
