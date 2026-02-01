package com.findy.boundedcontext.post.in.rest.request;

public record CreatePostRequest(
        Long authorId,
        String title,
        String content
) {
}
