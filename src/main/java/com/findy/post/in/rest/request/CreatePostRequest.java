package com.findy.post.in.rest.request;

public record CreatePostRequest(
        Long authorId,
        String title,
        String content
) {
}
