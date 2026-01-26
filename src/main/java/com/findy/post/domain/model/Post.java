package com.findy.post.domain.model;

import com.findy.common.domain.PositiveIntegerCounter;

public class Post {
    private final Long id;
    private final Long authorId;
    private final PostInfo postInfo;
    private final PositiveIntegerCounter likeCounter;

    public Post(Long id, Long authorId, PostInfo postInfo) {
        this.id = id;
        this.authorId = authorId;
        this.postInfo = postInfo;
        this.likeCounter = new PositiveIntegerCounter();
    }

    public void like() {
        this.likeCounter.increase();
    }

    public void unlike() {
        this.likeCounter.decrease();
    }
}
