package com.findy.topic.in.rest.response;

import com.findy.common.dto.Identifiable;

public record GetTopicResponse(
        Long id,
        Long categoryId,
        Long creatorId,
        String name,
        String introduction,
        String coverImageUrl,
        Long memberCount
) implements Identifiable {
}
