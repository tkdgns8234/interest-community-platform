package com.findy.user.domain.exception;

import com.findy.common.exception.DomainException;

public class InvalidUserInfoException extends DomainException {
    private static final String CODE = "USER_INVALID_INFO";

    public InvalidUserInfoException(String message) {
        super(CODE, message);
    }
}