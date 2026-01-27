package com.findy.post.app.dto;

public record UpdateCommentCommand(
        Long id,
        String content
) {
}
