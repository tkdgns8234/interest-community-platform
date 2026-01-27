package com.findy.post.domain.exception;

import com.findy.common.exception.DomainException;

public class ContentEmptyException extends DomainException {
    private static final String CODE = "EMPTY_POST_CONTENT";

    public ContentEmptyException() {
        super(CODE, "Post content cannot be empty.");
    }
}
