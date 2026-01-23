package com.findy.user.in.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequestDTO(
    @NotNull
    long id,
    String name,
    String nickname,
    String profileImageUrl
) {
}
