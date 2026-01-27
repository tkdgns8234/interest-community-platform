package com.findy.post.app.dto;

public record UpdatePostCommand(
        Long id,
        String title,
        String content
) {
}
