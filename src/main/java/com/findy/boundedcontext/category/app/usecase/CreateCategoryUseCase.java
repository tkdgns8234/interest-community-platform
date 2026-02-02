package com.findy.boundedcontext.category.app.usecase;

import com.findy.boundedcontext.category.app.dto.CreateCategoryCommand;
import com.findy.boundedcontext.category.app.interfaces.CategoryRepository;
import com.findy.boundedcontext.category.domain.model.Category;
import com.findy.boundedcontext.category.domain.model.CategoryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category execute(CreateCategoryCommand command) {
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
}
