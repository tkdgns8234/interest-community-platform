package com.findy.boundedcontext.category.app.dto;

import com.findy.boundedcontext.category.domain.model.Category;

import java.util.List;

public record CategoryWtihChildren(
        Category parent,
        List<Category> children
) {
}
