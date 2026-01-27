package com.findy.post.in.rest.response;

public record GetCommentResponse(
        Long id,
        Long postId,
        Long authorId,
        String content,
        Boolean isEdit,
        Long likeCount
) {
}
