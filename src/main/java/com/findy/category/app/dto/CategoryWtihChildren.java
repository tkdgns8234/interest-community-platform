package com.findy.category.app.dto;

import com.findy.category.domain.model.Category;

import java.util.List;

public record CategoryWtihChildren(
        Category parent,
        List<Category> children
) {
}
