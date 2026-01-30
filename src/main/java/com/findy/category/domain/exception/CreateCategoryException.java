package com.findy.category.domain.exception;

import com.findy.common.exception.DomainException;

public class CreateCategoryException extends DomainException {
    private static final String CODE = "CATEGORY_CREATE_ACTION";

    public CreateCategoryException(String message) {
        super(CODE, message);
    }
}
