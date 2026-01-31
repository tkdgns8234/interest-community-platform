package com.findy.topic.in.rest.mapper;

import com.findy.common.dto.CursorPageResponse;
import com.findy.topic.domain.model.TopicMembership;
import com.findy.topic.in.rest.response.GetTopicMembershipResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicMembershipRestMapper {
    public GetTopicMembershipResponse toGetTopicMembershipResponse(TopicMembership membership) {
        return new GetTopicMembershipResponse(
                membership.getId(),
                membership.getUserId(),
                membership.getTopicId(),
                membership.getRole(),
                membership.getJoinedAt()
        );
    }

    public CursorPageResponse<GetTopicMembershipResponse> toGetTopicMembershipPageResponse(
            List<TopicMembership> memberships, int size) {
        List<GetTopicMembershipResponse> responses = memberships.stream()
                .map(this::toGetTopicMembershipResponse)
                .toList();
        return CursorPageResponse.of(responses, size);
    }
}
