package com.findy.category.app.service;

import com.findy.category.app.dto.CategoryWtihChildren;
import com.findy.category.app.dto.CreateCategoryCommand;
import com.findy.category.app.interfaces.CategoryRepository;
import com.findy.category.domain.model.Category;
import com.findy.category.domain.model.CategoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category create(CreateCategoryCommand command) {
        Category category = new Category(
                null,
                command.parentId(),
                new CategoryInfo(
                        command.name(),
                        command.description(),
                        command.iconUrl()
                )
        );

        return categoryRepository.save(category);
    }

    public List<CategoryWtihChildren> getAllCategories() {
        return categoryRepository.getAll();
    }
}
