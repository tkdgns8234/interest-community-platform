package com.findy.category.app.interfaces;

import com.findy.category.app.dto.CategoryWtihChildren;
import com.findy.category.domain.model.Category;

import java.util.List;

public interface CategoryRepository {
    Category save(Category category);
    List<CategoryWtihChildren> getAll();
}
