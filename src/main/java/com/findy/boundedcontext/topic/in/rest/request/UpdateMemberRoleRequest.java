package com.findy.boundedcontext.topic.in.rest.request;

import com.findy.boundedcontext.topic.domain.model.membership.MemberRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull
        Long requesterId,
        @NotNull
        MemberRole role
) {
}
