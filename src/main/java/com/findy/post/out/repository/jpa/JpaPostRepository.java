package com.findy.post.out.repository.jpa;

import com.findy.post.out.repository.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<PostEntity, Long> {
}
