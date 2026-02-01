package com.findy.boundedcontext.topic.in.rest.request;

public record UpdateTopicRequest(
        Long userId,
        String name,
        String introduction,
        String coverImageUrl
) {
}
