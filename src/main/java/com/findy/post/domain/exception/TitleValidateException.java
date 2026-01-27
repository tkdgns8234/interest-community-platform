package com.findy.post.domain.exception;

import com.findy.common.exception.DomainException;

public class TitleValidateException extends DomainException {
    private static final String CODE = "INVALID_POST_TITLE";

    public TitleValidateException(String message) {
        super(CODE, message);
    }
}
