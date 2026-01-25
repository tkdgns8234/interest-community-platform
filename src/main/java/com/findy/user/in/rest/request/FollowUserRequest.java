package com.findy.user.in.rest.request;

import jakarta.validation.constraints.NotNull;

public record FollowUserRequest(
    @NotNull
    long userId,
    @NotNull
    long targetUserId
) { }
