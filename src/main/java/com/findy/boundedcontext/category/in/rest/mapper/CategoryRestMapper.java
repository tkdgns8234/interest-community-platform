package com.findy.boundedcontext.category.in.rest.mapper;

import com.findy.boundedcontext.category.app.dto.CategoryWithChildren;
import com.findy.boundedcontext.category.app.dto.CreateCategoryCommand;
import com.findy.boundedcontext.category.domain.model.Category;
import com.findy.boundedcontext.category.in.rest.request.CreateCategoryRequest;
import com.findy.boundedcontext.category.in.rest.response.CategoryResponse;
import com.findy.boundedcontext.category.in.rest.response.GetAllCategoryResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryRestMapper {
    public CreateCategoryCommand toCreateCategoryCommand(CreateCategoryRequest request) {
        return new CreateCategoryCommand(
                request.parentId(),
                request.name(),
                request.description(),
                request.iconUrl()
        );
    }

    public List<GetAllCategoryResponse> toGetAllCategoryResponse(List<CategoryWithChildren> categories) {
        return categories.stream()
                .map(category -> new GetAllCategoryResponse(
                        category.parent().getId(),
                        category.parent().getName(),
                        category.parent().getDescription(),
                        category.parent().getIconUrl(),
                        category.children().stream()
                                .map(children ->
                                        new CategoryResponse(
                                                children.getId(),
                                                children.getParentId(),
                                                children.getName(),
                                                children.getDescription(),
                                                children.getIconUrl()
                                        ))
                                .toList()
                ))
                .toList();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getParentId(),
                category.getName(),
                category.getDescription(),
                category.getIconUrl()
        );
    }

    public List<CategoryResponse> toCategoryResponseList(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryResponse)
                .toList();
    }
}
