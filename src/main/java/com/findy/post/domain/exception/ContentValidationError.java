package com.findy.post.domain.exception;

import com.findy.common.exception.DomainException;

public class ContentValidationError extends DomainException {
    private static final String CODE = "INVALID_POST_CONTENT";

    public ContentValidationError(String message) {
        super(CODE, message);
    }
}
