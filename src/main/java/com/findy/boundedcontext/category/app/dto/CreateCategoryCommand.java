package com.findy.boundedcontext.category.app.dto;

public record CreateCategoryCommand(
        Long parentId,
        String name,
        String description,
        String iconUrl
) {
}
