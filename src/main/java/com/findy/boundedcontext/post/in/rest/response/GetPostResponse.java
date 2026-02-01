package com.findy.boundedcontext.post.in.rest.response;

import com.findy.global.dto.Identifiable;

public record GetPostResponse(
        Long id,
        Long authorId,
        String title,
        String content,
        Long likeCount
) implements Identifiable {
}
