package com.findy.user.presentation.web.user.request;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequestDTO(
    @NotNull
    long id,
    String name,
    String nickname,
    String profileImageUrl
) {
}
