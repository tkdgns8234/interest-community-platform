package com.findy.boundedcontext.category.app.usecase;

import com.findy.boundedcontext.category.app.dto.CategoryWithChildren;
import com.findy.boundedcontext.category.app.interfaces.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryWithChildren> execute() {
        return categoryRepository.getAll();
    }
}
