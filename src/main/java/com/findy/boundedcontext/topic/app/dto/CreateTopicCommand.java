package com.findy.boundedcontext.topic.app.dto;

public record CreateTopicCommand(
        Long categoryId,
        Long creatorId,
        String name,
        String introduction,
        String coverImageUrl
) {
}
