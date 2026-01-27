package com.findy.post.app.dto;

public record CreatePostCommand(
        Long authorId,
        String title,
        String content
) {
}
