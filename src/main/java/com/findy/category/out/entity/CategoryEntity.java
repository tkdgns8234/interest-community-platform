package com.findy.category.out.entity;

import com.findy.category.domain.model.Category;
import com.findy.category.domain.model.CategoryInfo;
import com.findy.category.domain.model.CategoryType;
import com.findy.common.out.repository.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CategoryEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @Column(nullable = false)
    private String name;
    private String description;
    private String iconUrl;

    public CategoryEntity(Category category) {
        this.id = category.getId();
        this.parentId = category.getParentId();
        this.categoryType = category.getCategoryType();
        this.name = category.getName();
        this.description = category.getDescription();
        this.iconUrl = category.getIconUrl();
    }

    public Category toCategory() {
        return new Category(
                this.id,
                this.parentId,
                new CategoryInfo(this.name, this.description, this.iconUrl),
                this.categoryType
        );
    }
}
