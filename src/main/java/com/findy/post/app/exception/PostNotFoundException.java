package com.findy.post.app.exception;

import com.findy.common.exception.ApplicationException;

public class PostNotFoundException extends ApplicationException {
    private static final String CODE = "POST_NOT_FOUND";
    private static final int STATUS = 404;

    public PostNotFoundException(Long id) {
        super(CODE, "Post not found: " + id, STATUS);
    }
}
