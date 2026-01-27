package com.findy.post.app.exception;

import com.findy.common.exception.ApplicationException;

public class LikeAlreadyExistsException extends ApplicationException {
    private static final String CODE = "LIKE_ALREADY_EXISTS";
    private static final int STATUS = 400;

    public LikeAlreadyExistsException() {
        super(CODE, "Like already exists", STATUS);
    }
}
