package com.findy.topic.in.rest.request;

import jakarta.validation.constraints.NotNull;

public record UpdateTopicPostRequest(
        @NotNull
        Long userId,
        String title,
        String content
) {
}
