package com.findy.boundedcontext.topic.in.rest.response;

import com.findy.global.dto.Identifiable;
import com.findy.boundedcontext.topic.domain.model.membership.MemberRole;

public record GetTopicMembershipResponse(
        Long id,
        Long userId,
        Long topicId,
        MemberRole role
) implements Identifiable {
}
