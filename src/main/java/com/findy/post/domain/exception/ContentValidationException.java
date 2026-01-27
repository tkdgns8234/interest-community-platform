package com.findy.post.domain.exception;

import com.findy.common.exception.DomainException;

public class ContentValidationException extends DomainException {
    private static final String CODE = "INVALID_POST_CONTENT";

    public ContentValidationException(String message) {
        super(CODE, message);
    }
}
