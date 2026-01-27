package com.findy.post.app.exception;

import com.findy.common.exception.ApplicationException;

public class LikeNotFoundException extends ApplicationException {
    private static final String CODE = "LIKE_NOT_FOUND";
    private static final int STATUS = 404;

    public LikeNotFoundException() {
        super(CODE, "Like not found", STATUS);
    }
}
