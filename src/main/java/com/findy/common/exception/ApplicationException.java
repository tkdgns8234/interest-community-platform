package com.findy.common.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {
    private final String code;
    private final int status;

    protected ApplicationException(String code, String message, int status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}