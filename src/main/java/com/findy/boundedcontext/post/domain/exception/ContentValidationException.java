package com.findy.boundedcontext.post.domain.exception;

import com.findy.global.exception.DomainException;

public class ContentValidationException extends DomainException {
    private static final String CODE = "INVALID_POST_CONTENT";

    public ContentValidationException(String message) {
        super(CODE, message);
    }
}
