package com.findy.boundedcontext.post.domain.model.post;

import com.findy.boundedcontext.post.domain.model.LikeManager;
import lombok.Getter;

@Getter
public class Post {
    private final Long id;
    private final Long authorId;
    private PostInfo postInfo;
    private final LikeManager likeManager;

    public Post(Long id, Long authorId, PostInfo postInfo) {
        this(id, authorId, postInfo, new LikeManager());
    }

    public Post(Long id, Long authorId, PostInfo postInfo, LikeManager likeManager) {
        this.id = id;
        this.authorId = authorId;
        this.postInfo = postInfo;
        this.likeManager = likeManager;
    }

    public void updateTitle(String title) {
        this.postInfo = this.postInfo.withTitle(title);
    }

    public void updateContent(String content) {
        this.postInfo = this.postInfo.withContent(content);
    }

    public void like(Long userId) {
        this.likeManager.like(authorId, userId);
    }

    public void unlike(Long userId) {
        this.likeManager.unlike(authorId, userId);
    }
}
