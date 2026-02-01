package com.findy.boundedcontext.category.app.exception;

import com.findy.global.exception.ApplicationException;

public class CategoryNotFoundException extends ApplicationException {
    private final static String CODE = "CATEGORY_NOT_FOUND";
    private final static int STATUS = 404;

    public CategoryNotFoundException(Long categoryId) {
        super(CODE, "Category not found with id: " + categoryId, STATUS);
    }
}
