package com.findy.post.domain.exception;

import com.findy.common.exception.DomainException;

public class LikeValidationException extends DomainException {
    private static final String CODE = "INVALID_LIKE_ACTION";

    public LikeValidationException(String message) {
        super(CODE, message);
    }
}