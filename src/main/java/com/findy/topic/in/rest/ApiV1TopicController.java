package com.findy.topic.in.rest;

import com.findy.common.dto.CursorPageResponse;
import com.findy.common.dto.IdResponse;
import com.findy.topic.app.TopicService;
import com.findy.topic.domain.model.topic.Topic;
import com.findy.topic.in.rest.mapper.TopicRestMapper;
import com.findy.topic.in.rest.request.CreateTopicRequest;
import com.findy.topic.in.rest.request.DeleteTopicRequest;
import com.findy.topic.in.rest.request.UpdateTopicRequest;
import com.findy.topic.in.rest.response.GetTopicResponse;
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
@RequestMapping("/api/v1/topics")
@RequiredArgsConstructor
@Tag(name = "Topic", description = "토픽 API")
public class ApiV1TopicController {
    private final TopicService topicService;
    private final TopicRestMapper mapper;

    @PostMapping
    @Operation(summary = "토픽 생성", description = "새로운 토픽을 생성합니다")
    public ResponseEntity<IdResponse> createTopic(@RequestBody CreateTopicRequest request) {
        val command = mapper.toCreateCommand(request);
        Topic topic = topicService.createTopic(command);
        return ResponseEntity.ok(new IdResponse(topic.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "토픽 조회", description = "ID로 특정 토픽을 조회합니다")
    public ResponseEntity<GetTopicResponse> getTopic(@PathVariable Long id) {
        Topic topic = topicService.getTopic(id);
        val response = mapper.toGetTopicResponse(topic);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "토픽 목록 조회", description = "모든 토픽을 페이징하여 조회합니다")
    public ResponseEntity<CursorPageResponse<GetTopicResponse>> getAllTopics(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long categoryId
    ) {
        List<Topic> topics;
        if (categoryId != null) {
            topics = topicService.getTopicsByCategoryId(categoryId, cursor, size);
        } else {
            topics = topicService.getAllTopics(cursor, size);
        }
        val response = mapper.toGetTopicPageResponse(topics, size);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "토픽 수정", description = "토픽 정보를 수정합니다 (생성자만 가능)")
    public ResponseEntity<GetTopicResponse> updateTopic(
            @PathVariable Long id,
            @RequestBody UpdateTopicRequest request
    ) {
        val command = mapper.toUpdateCommand(id, request);
        Topic topic = topicService.updateTopic(command);
        val response = mapper.toGetTopicResponse(topic);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "토픽 삭제", description = "토픽을 삭제합니다 (생성자만 가능)")
    public ResponseEntity<Void> deleteTopic(
            @PathVariable Long id,
            @RequestBody DeleteTopicRequest request
    ) {
        topicService.deleteTopic(id, request.userId());
        return ResponseEntity.noContent().build();
    }
}
