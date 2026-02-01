package com.findy.boundedcontext.topic.in.rest.mapper;

import com.findy.global.dto.CursorPageResponse;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import com.findy.boundedcontext.topic.in.rest.response.GetTopicPostResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicPostRestMapper {
    public GetTopicPostResponse toGetTopicPostResponse(TopicPost post) {
        return new GetTopicPostResponse(
                post.getId(),
                post.getTopicId(),
                post.getAuthorId(),
                post.getTitle(),
                post.getContent(),
                post.getLikeCount()
        );
    }

    public CursorPageResponse<GetTopicPostResponse> toGetTopicPostPageResponse(
            List<TopicPost> posts, int size) {
        List<GetTopicPostResponse> responses = posts.stream()
                .map(this::toGetTopicPostResponse)
                .toList();
        return CursorPageResponse.of(responses, size);
    }
}
