package com.findy.boundedcontext.category.app.usecase;

import com.findy.boundedcontext.category.app.interfaces.CategoryRepository;
import com.findy.boundedcontext.category.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCategoryByIdUseCase {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category execute(Long id) {
        return categoryRepository.findById(id);
    }
}
