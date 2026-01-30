package com.findy.category.in.rest.mapper;

import com.findy.category.app.dto.CategoryWtihChildren;
import com.findy.category.app.dto.CreateCategoryCommand;
import com.findy.category.in.rest.request.CreateCategoryRequest;
import com.findy.category.in.rest.response.CategoryResponse;
import com.findy.category.in.rest.response.GetAllCategoryResponse;
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

    public List<GetAllCategoryResponse> toGetAllCategoryResponse(List<CategoryWtihChildren> categories) {
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

}
