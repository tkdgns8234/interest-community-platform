package com.findy.category.in.rest;

import com.findy.category.app.dto.CategoryWtihChildren;
import com.findy.category.app.service.CategoryService;
import com.findy.category.domain.model.Category;
import com.findy.category.in.rest.mapper.CategoryRestMapper;
import com.findy.category.in.rest.request.CreateCategoryRequest;
import com.findy.category.in.rest.response.GetAllCategoryResponse;
import com.findy.common.dto.IdResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class ApiV1CategoryController {
    private final CategoryRestMapper mapper;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<IdResponse> save(@RequestBody CreateCategoryRequest request) {
        val command = mapper.toCreateCategoryCommand(request);
        Category category = categoryService.create(command);
        return ResponseEntity.ok(new IdResponse(category.getId()));
    }

    @GetMapping
    public ResponseEntity<List<GetAllCategoryResponse>> getAll() {
        List<CategoryWtihChildren> categories = categoryService.getAllCategories();
        val response = mapper.toGetAllCategoryResponse(categories);
        return ResponseEntity.ok(response);
    }
}
