package com.findy.category.in.rest.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @Nullable
        Long parentId,
        @NotNull
        @Size(max = 32)
        String name,
        String description,
        String iconUrl
) {
}
