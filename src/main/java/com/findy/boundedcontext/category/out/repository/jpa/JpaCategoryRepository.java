package com.findy.boundedcontext.category.out.repository.jpa;

import com.findy.boundedcontext.category.out.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
