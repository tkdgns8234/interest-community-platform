package com.findy.boundedcontext.topic.in.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicPostRequest(
        @NotNull
        Long authorId,
        @NotBlank
        String title,
        @NotBlank
        String content
) {
}
