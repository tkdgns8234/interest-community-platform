package com.findy.post.domain.model;

import com.findy.common.domain.PositiveIntegerCounter;
import com.findy.post.domain.exception.LikeValidationException;

public class LikeManager {
    private PositiveIntegerCounter likeCounter;

    public LikeManager() {
        this.likeCounter = new PositiveIntegerCounter();
    }

    public LikeManager(Long count) {
        this.likeCounter = new PositiveIntegerCounter(count);
    }

    public Long getCount() {
        return likeCounter.getCount();
    }

    public void like(Long authorId, Long userId) {
        validateAuthor(authorId, userId);
        this.likeCounter = this.likeCounter.increase();
    }

    public void unlike(Long authorId, Long userId) {
        validateAuthor(authorId, userId);
        this.likeCounter = this.likeCounter.decrease();
    }

    private void validateAuthor(Long authorId, Long userId) {
        if (authorId.equals(userId)) {
            throw new LikeValidationException("Author cannot like/unlike their own");
        }
    }
}
