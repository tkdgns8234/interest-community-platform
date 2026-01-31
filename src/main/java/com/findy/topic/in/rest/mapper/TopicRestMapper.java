package com.findy.topic.in.rest.mapper;

import com.findy.common.dto.CursorPageResponse;
import com.findy.topic.app.dto.CreateTopicCommand;
import com.findy.topic.app.dto.UpdateTopicCommand;
import com.findy.topic.domain.model.Topic;
import com.findy.topic.in.rest.request.CreateTopicRequest;
import com.findy.topic.in.rest.request.UpdateTopicRequest;
import com.findy.topic.in.rest.response.GetTopicResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicRestMapper {
    public CreateTopicCommand toCreateCommand(CreateTopicRequest request) {
        return new CreateTopicCommand(
                request.categoryId(),
                request.creatorId(),
                request.name(),
                request.introduction(),
                request.coverImageUrl()
        );
    }

    public UpdateTopicCommand toUpdateCommand(Long topicId, UpdateTopicRequest request) {
        return new UpdateTopicCommand(
                topicId,
                request.userId(),
                request.name(),
                request.introduction(),
                request.coverImageUrl()
        );
    }

    public GetTopicResponse toGetTopicResponse(Topic topic) {
        return new GetTopicResponse(
                topic.getId(),
                topic.getCategoryId(),
                topic.getCreatorId(),
                topic.getName(),
                topic.getIntroduction(),
                topic.getCoverImageUrl(),
                topic.getMemberCount()
        );
    }

    public CursorPageResponse<GetTopicResponse> toGetTopicPageResponse(List<Topic> topics, int size) {
        List<GetTopicResponse> responses = topics.stream()
                .map(this::toGetTopicResponse)
                .toList();
        return CursorPageResponse.of(responses, size);
    }
}
