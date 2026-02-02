package com.findy.boundedcontext.topic.in.rest;

import com.findy.global.dto.CursorPageResponse;
import com.findy.boundedcontext.topic.app.usecase.GetMembershipUseCase;
import com.findy.boundedcontext.topic.app.usecase.GetTopicMembersUseCase;
import com.findy.boundedcontext.topic.app.usecase.JoinTopicUseCase;
import com.findy.boundedcontext.topic.app.usecase.LeaveTopicUseCase;
import com.findy.boundedcontext.topic.app.usecase.UpdateMemberRoleUseCase;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import com.findy.boundedcontext.topic.in.rest.mapper.TopicMembershipRestMapper;
import com.findy.boundedcontext.topic.in.rest.request.JoinTopicRequest;
import com.findy.boundedcontext.topic.in.rest.request.LeaveTopicRequest;
import com.findy.boundedcontext.topic.in.rest.request.UpdateMemberRoleRequest;
import com.findy.boundedcontext.topic.in.rest.response.GetTopicMembershipResponse;
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
@RequestMapping("/api/v1/topics/{topicId}/memberships")
@RequiredArgsConstructor
@Tag(name = "Topic Membership", description = "토픽 멤버십 API")
public class ApiV1TopicMembershipController {
    private final JoinTopicUseCase joinTopicUseCase;
    private final LeaveTopicUseCase leaveTopicUseCase;
    private final GetMembershipUseCase getMembershipUseCase;
    private final GetTopicMembersUseCase getTopicMembersUseCase;
    private final UpdateMemberRoleUseCase updateMemberRoleUseCase;
    private final TopicMembershipRestMapper mapper;

    @PostMapping
    @Operation(summary = "토픽 가입", description = "사용자가 토픽에 가입합니다")
    public ResponseEntity<GetTopicMembershipResponse> joinTopic(
            @PathVariable Long topicId,
            @RequestBody JoinTopicRequest request
    ) {
        TopicMembership membership = joinTopicUseCase.execute(request.userId(), topicId);
        val response = mapper.toGetTopicMembershipResponse(membership);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Operation(summary = "토픽 탈퇴", description = "사용자가 토픽에서 탈퇴합니다")
    public ResponseEntity<Void> leaveTopic(
            @PathVariable Long topicId,
            @RequestBody LeaveTopicRequest request
    ) {
        leaveTopicUseCase.execute(request.userId(), topicId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "멤버십 조회", description = "특정 사용자의 토픽 멤버십을 조회합니다")
    public ResponseEntity<GetTopicMembershipResponse> getMembership(
            @PathVariable Long topicId,
            @PathVariable Long userId
    ) {
        TopicMembership membership = getMembershipUseCase.execute(userId, topicId);
        val response = mapper.toGetTopicMembershipResponse(membership);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "토픽 멤버 목록 조회", description = "토픽의 모든 멤버를 페이징하여 조회합니다")
    public ResponseEntity<CursorPageResponse<GetTopicMembershipResponse>> getTopicMembers(
            @PathVariable Long topicId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<TopicMembership> memberships = getTopicMembersUseCase.execute(topicId, cursor, size);
        val response = mapper.toGetTopicMembershipPageResponse(memberships, size);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/role")
    @Operation(summary = "멤버 역할 변경", description = "멤버의 역할을 변경합니다 (생성자만 가능)")
    public ResponseEntity<GetTopicMembershipResponse> updateMemberRole(
            @PathVariable Long topicId,
            @PathVariable Long userId,
            @RequestBody UpdateMemberRoleRequest request
    ) {
        TopicMembership membership = updateMemberRoleUseCase.execute(
                request.requesterId(),
                userId,
                topicId,
                request.role()
        );
        val response = mapper.toGetTopicMembershipResponse(membership);
        return ResponseEntity.ok(response);
    }
}
