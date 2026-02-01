package com.findy.boundedcontext.topic.in.rest;

import com.findy.global.dto.CursorPageResponse;
import com.findy.boundedcontext.topic.app.TopicMembershipService;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import com.findy.boundedcontext.topic.in.rest.mapper.TopicMembershipRestMapper;
import com.findy.boundedcontext.topic.in.rest.response.GetTopicMembershipResponse;
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
@RequestMapping("/api/v1/users/{userId}/topics")
@RequiredArgsConstructor
@Tag(name = "User Topics", description = "사용자 토픽 API")
public class ApiV1UserTopicController {
    private final TopicMembershipService membershipService;
    private final TopicMembershipRestMapper mapper;

    @GetMapping
    @Operation(summary = "사용자 가입 토픽 목록 조회", description = "사용자가 가입한 모든 토픽을 페이징하여 조회합니다")
    public ResponseEntity<CursorPageResponse<GetTopicMembershipResponse>> getUserTopics(
            @PathVariable Long userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<TopicMembership> memberships = membershipService.getUserTopics(userId, cursor, size);
        val response = mapper.toGetTopicMembershipPageResponse(memberships, size);
        return ResponseEntity.ok(response);
    }
}
