package com.findy.boundedcontext.post.app.exception;

import com.findy.global.exception.ApplicationException;

public class CommentNotFoundException extends ApplicationException {
    private static final String CODE = "COMMENT_NOT_FOUND";
    private static final int STATUS = 404;

    public CommentNotFoundException(Long id) {
        super(CODE, "Comment not found: " + id, STATUS);
    }
}
