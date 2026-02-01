package com.findy.boundedcontext.topic.in.rest;

import com.findy.global.dto.CursorPageResponse;
import com.findy.boundedcontext.topic.app.TopicPostService;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import com.findy.boundedcontext.topic.in.rest.mapper.TopicPostRestMapper;
import com.findy.boundedcontext.topic.in.rest.response.GetTopicPostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/topic-posts")
@RequiredArgsConstructor
@Tag(name = "User Topic Posts", description = "사용자 토픽 게시글 API")
public class ApiV1UserTopicPostController {
    private final TopicPostService topicPostService;
    private final TopicPostRestMapper mapper;

    @GetMapping
    @Operation(summary = "사용자 토픽 게시글 목록 조회", description = "사용자가 작성한 모든 토픽 게시글을 페이징하여 조회합니다")
    public ResponseEntity<CursorPageResponse<GetTopicPostResponse>> getUserTopicPosts(
            @PathVariable Long userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<TopicPost> posts = topicPostService.getPostsByAuthorId(userId, cursor, size);
        val response = mapper.toGetTopicPostPageResponse(posts, size);
        return ResponseEntity.ok(response);
    }
}
