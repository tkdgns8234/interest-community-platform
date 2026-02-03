package com.findy.boundedcontext.post.out.repository.entity;

import com.findy.boundedcontext.post.domain.model.LikeManager;
import com.findy.global.entity.BaseTimeEntity;
import com.findy.boundedcontext.post.domain.model.comment.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(
    name = "comment",
    indexes = {
        @Index(name = "idx_comment_post_id", columnList = "post_id")
    }
)
public class CommentEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
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
        return new Comment(id, postId, authorId, content, isEdit, new LikeManager(likeCount));
    }
}