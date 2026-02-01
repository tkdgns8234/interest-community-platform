package com.findy.topic.in.rest.response;

import com.findy.common.dto.Identifiable;

public record GetTopicPostResponse(
        Long id,
        Long topicId,
        Long authorId,
        String title,
        String content,
        Long likeCount
) implements Identifiable {
}
