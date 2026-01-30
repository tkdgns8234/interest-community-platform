package com.findy.category.in.rest;

import com.findy.category.app.dto.CategoryWithChildren;
import com.findy.category.app.service.CategoryService;
import com.findy.category.domain.model.Category;
import com.findy.category.in.rest.mapper.CategoryRestMapper;
import com.findy.category.in.rest.request.CreateCategoryRequest;
import com.findy.category.in.rest.response.CategoryResponse;
import com.findy.category.in.rest.response.GetAllCategoryResponse;
import com.findy.common.dto.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "카테고리 API")
public class ApiV1CategoryController {
    private final CategoryRestMapper mapper;
    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다")
    public ResponseEntity<IdResponse> save(@RequestBody CreateCategoryRequest request) {
        val command = mapper.toCreateCategoryCommand(request);
        Category category = categoryService.create(command);
        return ResponseEntity.ok(new IdResponse(category.getId()));
    }

    @GetMapping
    @Operation(summary = "전체 카테고리 조회", description = "모든 카테고리를 계층 구조로 조회합니다")
    public ResponseEntity<List<GetAllCategoryResponse>> getAll() {
        List<CategoryWithChildren> categories = categoryService.getAllCategories();
        val response = mapper.toGetAllCategoryResponse(categories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "카테고리 단건 조회", description = "ID로 특정 카테고리를 조회합니다")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        val response = mapper.toCategoryResponse(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{parentId}/children")
    @Operation(summary = "자식 카테고리 조회", description = "부모 카테고리의 모든 자식 카테고리를 조회합니다")
    public ResponseEntity<List<CategoryResponse>> getChildren(@PathVariable Long parentId) {
        List<Category> children = categoryService.getChildrenByParentId(parentId);
        val response = mapper.toCategoryResponseList(children);
        return ResponseEntity.ok(response);
    }
}
