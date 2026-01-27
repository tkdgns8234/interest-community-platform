package com.findy.post.out.repository;

import com.findy.post.app.exception.CommentNotFoundException;
import com.findy.post.app.interfaces.CommentRepository;
import com.findy.post.domain.model.comment.Comment;
import com.findy.post.out.repository.entity.CommentEntity;
import com.findy.post.out.repository.jpa.JpaCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final JpaCommentRepository jpaCommentRepository;

    @Override
    public Comment save(Comment comment) {
        CommentEntity commentEntity = new CommentEntity(comment);
        commentEntity = jpaCommentRepository.save(commentEntity);
        return commentEntity.toComment();
    }

    @Override
    public Comment findById(Long id) {
        CommentEntity commentEntity = jpaCommentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        return commentEntity.toComment();
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaCommentRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }
        jpaCommentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return jpaCommentRepository.findByPostId(postId)
                .stream()
                .map(CommentEntity::toComment)
                .toList();
    }
}
