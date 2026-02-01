package com.findy.boundedcontext.category.app.service;

import com.findy.boundedcontext.category.app.dto.CategoryWithChildren;
import com.findy.boundedcontext.category.app.dto.CreateCategoryCommand;
import com.findy.boundedcontext.category.app.interfaces.CategoryRepository;
import com.findy.boundedcontext.category.domain.model.Category;
import com.findy.boundedcontext.category.domain.model.CategoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
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

    @Transactional(readOnly = true)
    public List<CategoryWithChildren> getAllCategories() {
        return categoryRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Category> getChildrenByParentId(Long parentId) {
        // 부모 카테고리 존재 확인
        categoryRepository.findById(parentId);
        return categoryRepository.findChildrenByParentId(parentId);
    }
}
