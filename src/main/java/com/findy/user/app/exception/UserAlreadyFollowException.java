package com.findy.user.app.exception;

import com.findy.common.exception.ApplicationException;

public class UserAlreadyFollowException extends ApplicationException {
    private static final String CODE = "USER_ALREADY_FOLLOW";
    private static final int STATUS = 400;

    public UserAlreadyFollowException() {
        super(CODE, "", STATUS);
    }
}
