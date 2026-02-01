package com.findy.boundedcontext.post.app.dto;

public record UpdateCommentCommand(
        Long id,
        String content
) {
}
