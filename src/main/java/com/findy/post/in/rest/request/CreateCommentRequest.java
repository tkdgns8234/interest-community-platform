package com.findy.post.in.rest.request;

public record CreateCommentRequest(
        Long authorId,
        String content
) {
}
