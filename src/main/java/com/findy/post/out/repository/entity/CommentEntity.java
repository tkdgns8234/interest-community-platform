package com.findy.post.out.repository.entity;

import com.findy.common.out.repository.entity.BaseTimeEntity;
import com.findy.post.domain.model.comment.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;
    private Long authorId;

    @Lob
    private String content;

    private Boolean isEdit;
    private Long likeCount;

    public CommentEntity(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.authorId = comment.getAuthorId();
        this.content = comment.getContent();
        this.isEdit = comment.getIsEdit();
        this.likeCount = comment.getLikeCount();
    }

    public Comment toComment() {
        return new Comment(id, postId, authorId, content, isEdit, new com.findy.post.domain.model.LikeManager(likeCount));
    }
}