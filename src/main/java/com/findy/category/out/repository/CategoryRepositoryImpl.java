package com.findy.category.out.repository;

import com.findy.category.app.dto.CategoryWtihChildren;
import com.findy.category.app.interfaces.CategoryRepository;
import com.findy.category.domain.model.Category;
import com.findy.category.domain.model.CategoryType;
import com.findy.category.out.entity.CategoryEntity;
import com.findy.category.out.repository.jpa.JpaCategoryRepository;
import com.findy.category.out.repository.querydsl.CategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity(category);
        categoryEntity = jpaCategoryRepository.save(categoryEntity);
        return categoryEntity.toCategory();
    }

    @Override
    public List<CategoryWtihChildren> getAll() {
        // Parent 카테고리 조회
        List<CategoryEntity> parents = categoryQueryRepository.findAll(CategoryType.PARENT);

        // Children 카테고리 조회
        List<CategoryEntity> allChildren = categoryQueryRepository.findAll(CategoryType.CHILDREN);

        // 부모 ID로 그룹핑
        Map<Long, List<Category>> childrenMap = allChildren.stream()
                .map(CategoryEntity::toCategory)
                .collect(Collectors.groupingBy(Category::getParentId));

        // 조합
        return parents.stream()
                .map(parent -> new CategoryWtihChildren(
                        parent.toCategory(),
                        childrenMap.getOrDefault(parent.getId(), List.of())
                ))
                .toList();
    }
}
