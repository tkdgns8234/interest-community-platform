package com.findy.category.app.exception;

import com.findy.common.exception.ApplicationException;

public class CategoryNotFoundException extends ApplicationException {
    public CategoryNotFoundException(Long categoryId) {
        super("Category not found with id: " + categoryId);
    }
}
