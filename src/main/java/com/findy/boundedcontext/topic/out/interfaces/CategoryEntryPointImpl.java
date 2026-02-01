package com.findy.boundedcontext.topic.out.interfaces;

import com.findy.boundedcontext.category.app.interfaces.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryEntryPointImpl implements CategoryEntryPoint {
    private final CategoryRepository categoryRepository;

    @Override
    public void validateCategoryExists(Long categoryId) {
        // CategoryNotFoundException will be thrown if not found
        categoryRepository.findById(categoryId);
    }
}
