package com.findy.topic.in.rest.request;

import com.findy.topic.domain.model.MemberRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull
        Long requesterId,
        @NotNull
        MemberRole role
) {
}
