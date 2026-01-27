package com.findy.post.domain.model.post;

import com.findy.post.domain.model.LikeManager;
import lombok.Getter;

@Getter
public class Post {
    private final Long id;
    private final Long authorId;
    private final PostInfo postInfo;
    private final LikeManager likeManager;

    public Post(Long id, Long authorId, PostInfo postInfo) {
        this.id = id;
        this.authorId = authorId;
        this.postInfo = postInfo;
        this.likeManager = new LikeManager();
    }

    public void setTitle(String title) {
        this.postInfo.setTitle(title);
    }

    public void setContent(String content) {
        this.postInfo.setContent(content);
    }

    public void like(Long userId) {
        this.likeManager.like(authorId, userId);
    }

    public void unlike(Long userId) {
        this.likeManager.unlike(authorId, userId);
    }
}
