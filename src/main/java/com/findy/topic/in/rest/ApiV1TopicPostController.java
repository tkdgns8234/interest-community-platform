package com.findy.topic.in.rest;

import com.findy.common.dto.CursorPageResponse;
import com.findy.common.dto.IdResponse;
import com.findy.topic.app.TopicPostService;
import com.findy.topic.domain.model.post.TopicPost;
import com.findy.topic.in.rest.mapper.TopicPostRestMapper;
import com.findy.topic.in.rest.request.CreateTopicPostRequest;
import com.findy.topic.in.rest.request.DeleteTopicPostRequest;
import com.findy.topic.in.rest.request.UpdateTopicPostRequest;
import com.findy.topic.in.rest.response.GetTopicPostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topics/{topicId}/posts")
@RequiredArgsConstructor
@Tag(name = "Topic Post", description = "토픽 게시글 API")
public class ApiV1TopicPostController {
    private final TopicPostService topicPostService;
    private final TopicPostRestMapper mapper;

    @PostMapping
    @Operation(summary = "토픽 게시글 작성", description = "토픽 내 게시글을 작성합니다 (토픽 멤버만 가능)")
    public ResponseEntity<IdResponse> createPost(
            @PathVariable Long topicId,
            @RequestBody CreateTopicPostRequest request
    ) {
        TopicPost post = topicPostService.createPost(
                topicId,
                request.authorId(),
                request.title(),
                request.content()
        );
        return ResponseEntity.ok(new IdResponse(post.getId()));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "토픽 게시글 조회", description = "특정 토픽 게시글을 조회합니다")
    public ResponseEntity<GetTopicPostResponse> getPost(@PathVariable Long postId) {
        TopicPost post = topicPostService.getPost(postId);
        val response = mapper.toGetTopicPostResponse(post);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "토픽 게시글 목록 조회", description = "토픽의 모든 게시글을 페이징하여 조회합니다")
    public ResponseEntity<CursorPageResponse<GetTopicPostResponse>> getPostsByTopic(
            @PathVariable Long topicId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<TopicPost> posts = topicPostService.getPostsByTopicId(topicId, cursor, size);
        val response = mapper.toGetTopicPostPageResponse(posts, size);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "토픽 게시글 수정", description = "토픽 게시글을 수정합니다 (작성자만 가능)")
    public ResponseEntity<GetTopicPostResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdateTopicPostRequest request
    ) {
        TopicPost post = topicPostService.updatePost(
                postId,
                request.userId(),
                request.title(),
                request.content()
        );
        val response = mapper.toGetTopicPostResponse(post);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "토픽 게시글 삭제", description = "토픽 게시글을 삭제합니다 (작성자만 가능)")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestBody DeleteTopicPostRequest request
    ) {
        topicPostService.deletePost(postId, request.userId());
        return ResponseEntity.noContent().build();
    }
}
