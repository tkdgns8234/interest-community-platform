package com.findy.topic.in.rest.request;

import jakarta.validation.constraints.NotNull;

public record LeaveTopicRequest(
        @NotNull
        Long userId
) {
}