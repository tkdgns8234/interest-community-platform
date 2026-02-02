package com.findy.boundedcontext.category.app.usecase;

import com.findy.boundedcontext.category.app.interfaces.CategoryRepository;
import com.findy.boundedcontext.category.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetChildrenByParentIdUseCase {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> execute(Long parentId) {
        // 부모 카테고리 존재 확인
        categoryRepository.findById(parentId);
        return categoryRepository.findChildrenByParentId(parentId);
    }
}
