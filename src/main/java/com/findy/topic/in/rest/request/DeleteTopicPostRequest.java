package com.findy.topic.in.rest.request;

import jakarta.validation.constraints.NotNull;

public record DeleteTopicPostRequest(
        @NotNull
        Long userId
) {
}
