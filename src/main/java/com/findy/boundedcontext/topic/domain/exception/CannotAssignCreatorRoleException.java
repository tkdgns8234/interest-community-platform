package com.findy.boundedcontext.topic.domain.exception;

import com.findy.global.exception.DomainException;

public class CannotAssignCreatorRoleException extends DomainException {
    private static final String CODE = "TOPIC_CANNOT_ASSIGN_CREATOR_ROLE";

    public CannotAssignCreatorRoleException() {
        super(CODE, "Cannot assign CREATOR role to member");
    }
}
