package com.findy.topic.in.rest.request;

import jakarta.validation.constraints.NotNull;

public record DeleteTopicRequest(
        @NotNull
        Long userId,

) {
}
