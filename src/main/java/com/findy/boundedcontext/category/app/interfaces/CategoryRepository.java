package com.findy.boundedcontext.category.app.interfaces;

import com.findy.boundedcontext.category.app.dto.CategoryWithChildren;
import com.findy.boundedcontext.category.domain.model.Category;

import java.util.List;

public interface CategoryRepository {
    Category save(Category category);
    List<CategoryWithChildren> getAll();
    Category findById(Long id);
    List<Category> findChildrenByParentId(Long parentId);
}
