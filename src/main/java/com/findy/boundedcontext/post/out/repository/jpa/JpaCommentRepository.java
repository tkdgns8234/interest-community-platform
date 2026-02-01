package com.findy.boundedcontext.post.out.repository.jpa;

import com.findy.boundedcontext.post.out.repository.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostId(Long postId);
}