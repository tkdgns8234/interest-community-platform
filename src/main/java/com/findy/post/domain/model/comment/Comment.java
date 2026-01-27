package com.findy.post.domain.model.comment;

import com.findy.post.domain.exception.ContentEmptyException;
import com.findy.post.domain.model.LikeManager;
import lombok.Getter;

@Getter
public class Comment {
    private final Long id;
    private Long postId;
    private Long authorId;
    private String content;
    private Boolean isEdit;
    private LikeManager likeManager;

    public Comment(Long id, Long postId, Long authorId, String content) {
        this(id, postId, authorId, content, new LikeManager());
    }

    public Comment(Long id, Long postId, Long authorId, String content, LikeManager likeManager) {
        this(id, postId, authorId, content, false, likeManager);
    }

    public Comment(Long id, Long postId, Long authorId, String content, Boolean isEdit, LikeManager likeManager) {
        validateContent(content);

        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.isEdit = isEdit;
        this.likeManager = likeManager;
    }

    public void updateContent(String content) {
        validateContent(content);

        this.content = content;
        this.isEdit = true;
    }

    public Long getLikeCount() {
        return likeManager.getCount();
    }

    public void like(Long userId) {
        this.likeManager.like(authorId, userId);
    }

    public void unlike(Long userId) {
        this.likeManager.unlike(authorId, userId);
    }

    private void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new ContentEmptyException();
        }
    }
}
