package com.findy.boundedcontext.post.app.dto;

public record CreatePostCommand(
        Long authorId,
        String title,
        String content
) {
}
