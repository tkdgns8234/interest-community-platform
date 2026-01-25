package com.findy.user.in.rest.request;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
    @NotNull
    long id,
    String name,
    String nickname,
    String profileImageUrl
) {
}
