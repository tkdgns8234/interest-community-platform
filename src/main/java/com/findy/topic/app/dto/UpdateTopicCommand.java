package com.findy.topic.app.dto;

public record UpdateTopicCommand(
        Long topicId,
        Long userId,
        String name,
        String introduction,
        String coverImageUrl
) {
}
