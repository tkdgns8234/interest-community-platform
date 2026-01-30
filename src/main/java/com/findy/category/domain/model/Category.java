package com.findy.category.domain.model;

import lombok.Getter;

@Getter
public class Category {
    private Long id;
    private Long parentId;
    private CategoryInfo categoryInfo;
    private CategoryType categoryType;

    public Category(Long id, Long parentId, CategoryInfo categoryInfo) {
        this(id, parentId, categoryInfo, (parentId == null) ? CategoryType.PARENT : CategoryType.CHILD);
    }

    public Category(Long id, Long parentId, CategoryInfo categoryInfo, CategoryType categoryType) {
        this.id = id;
        this.parentId = parentId;
        this.categoryType = categoryType;
        this.categoryInfo = categoryInfo;
    }

    public String getName() {
        return categoryInfo.getName();
    }

    public String getDescription() {
        return categoryInfo.getDescription();
    }

    public String getIconUrl() {
        return categoryInfo.getIconUrl();
    }
}
