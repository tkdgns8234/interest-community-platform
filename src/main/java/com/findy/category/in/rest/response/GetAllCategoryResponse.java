package com.findy.category.in.rest.response;

import java.util.List;

public record GetAllCategoryResponse(
        Long id,
        String name,
        String description,
        String iconUrl,
        List<CategoryResponse> children
) {

}
