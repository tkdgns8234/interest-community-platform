package com.findy.topic.in.rest.request;

import jakarta.validation.constraints.NotNull;

public record JoinTopicRequest(
        @NotNull
        Long userId
) {
}