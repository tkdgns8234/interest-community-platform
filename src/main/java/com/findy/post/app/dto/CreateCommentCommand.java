package com.findy.post.app.dto;

public record CreateCommentCommand(
        Long postId,
        Long authorId,
        String content
) {
}
