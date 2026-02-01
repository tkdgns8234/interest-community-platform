package com.findy.boundedcontext.user.app.exception;

import com.findy.global.exception.ApplicationException;

public class UserNotFoundException extends ApplicationException {
    private static final String CODE = "USER_NOT_FOUND";
    private static final int STATUS = 404;

    public UserNotFoundException(Long userId) {
        super(CODE, "User not found: " + userId, STATUS);
    }
}