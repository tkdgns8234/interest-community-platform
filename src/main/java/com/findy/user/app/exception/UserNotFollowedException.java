package com.findy.user.app.exception;

import com.findy.common.exception.ApplicationException;

public class UserNotFollowedException extends ApplicationException {
    private static final String CODE = "USER_NOT_FOLLOWED";
    private static final int STATUS = 400;

    public UserNotFollowedException() {
        super(CODE, "", STATUS);
    }
}
