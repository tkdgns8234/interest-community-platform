package com.findy.boundedcontext.topic.in.rest.request;

public record CreateTopicRequest(
        Long categoryId,
        Long creatorId,
        String name,
        String introduction,
        String coverImageUrl
) {
}
