package com.findy.post.in.rest.response;

import com.findy.common.dto.Identifiable;

public record GetPostResponse(
        Long id,
        Long authorId,
        String title,
        String content,
        Long likeCount
) implements Identifiable {
}
