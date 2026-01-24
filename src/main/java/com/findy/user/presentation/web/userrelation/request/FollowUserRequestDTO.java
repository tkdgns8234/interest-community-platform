package com.findy.user.presentation.web.userrelation.request;

import jakarta.validation.constraints.NotNull;

public record FollowUserRequestDTO(
    @NotNull
    long userId,
    @NotNull
    long targetUserId
) { }
