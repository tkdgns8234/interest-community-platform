package com.findy.topic.in.rest.response;

import com.findy.common.dto.Identifiable;
import com.findy.topic.domain.model.MemberRole;

import java.time.LocalDateTime;

public record GetTopicMembershipResponse(
        Long id,
        Long userId,
        Long topicId,
        MemberRole role,
        LocalDateTime joinedAt
) implements Identifiable {
}
