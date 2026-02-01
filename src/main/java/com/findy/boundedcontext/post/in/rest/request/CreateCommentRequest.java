package com.findy.boundedcontext.post.in.rest.request;

public record CreateCommentRequest(
        Long authorId,
        String content
) {
}
