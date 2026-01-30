package com.findy.category.in.rest.response;

public record CategoryResponse(
        Long id,
        Long parentId,
        String name,
        String description,
        String iconUrl
) {
}
