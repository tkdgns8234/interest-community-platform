package com.findy.post.domain.exception;

import com.findy.common.exception.DomainException;

public class TitleValidationError extends DomainException {
    private static final String CODE = "INVALID_POST_TITLE";

    public TitleValidationError(String message) {
        super(CODE, message);
    }
}
