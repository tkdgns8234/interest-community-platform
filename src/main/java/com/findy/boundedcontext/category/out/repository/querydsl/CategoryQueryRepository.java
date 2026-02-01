package com.findy.boundedcontext.category.out.repository.querydsl;

import com.findy.boundedcontext.category.domain.model.CategoryType;
import com.findy.boundedcontext.category.out.entity.CategoryEntity;
import com.findy.boundedcontext.category.out.entity.QCategoryEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<CategoryEntity> findAll(CategoryType categoryType) {
        QCategoryEntity category = QCategoryEntity.categoryEntity;
        BooleanBuilder builder = new BooleanBuilder();

        switch (categoryType) {
            case PARENT -> {
                builder.and(category.parentId.isNull());
                builder.and(category.categoryType.eq(CategoryType.PARENT));
            }
            case CHILD -> {
                builder.and(category.parentId.isNotNull());
                builder.and(category.categoryType.eq(CategoryType.CHILD));
            }
        }

        return queryFactory
                .selectFrom(category)
                .where(builder)
                .orderBy(category.name.asc())
                .fetch();
    }

    public List<CategoryEntity> findByParentId(Long parentId) {
        QCategoryEntity category = QCategoryEntity.categoryEntity;

        return queryFactory
                .selectFrom(category)
                .where(category.parentId.eq(parentId))
                .orderBy(category.name.asc())
                .fetch();
    }
}
