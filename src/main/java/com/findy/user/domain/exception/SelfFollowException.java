package com.findy.user.domain.exception;

import com.findy.common.exception.DomainException;

public class SelfFollowException extends DomainException {
    private static final String CODE = "USER_SELF_FOLLOW";

    public SelfFollowException(String message) {
        super(CODE, message);
    }
}