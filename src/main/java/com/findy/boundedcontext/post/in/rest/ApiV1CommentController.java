package com.findy.boundedcontext.post.in.rest;

import com.findy.global.dto.IdResponse;
import com.findy.boundedcontext.post.app.usecase.CreateCommentUseCase;
import com.findy.boundedcontext.post.app.usecase.DeleteCommentUseCase;
import com.findy.boundedcontext.post.app.usecase.GetCommentUseCase;
import com.findy.boundedcontext.post.app.usecase.GetCommentsByPostIdUseCase;
import com.findy.boundedcontext.post.app.usecase.UpdateCommentUseCase;
import com.findy.boundedcontext.post.domain.model.comment.Comment;
import com.findy.boundedcontext.post.in.rest.mapper.CommentRestMapper;
import com.findy.boundedcontext.post.in.rest.request.CreateCommentRequest;
import com.findy.boundedcontext.post.in.rest.request.UpdateCommentRequest;
import com.findy.boundedcontext.post.in.rest.response.GetCommentResponse;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post/{postId}/comment")
public class ApiV1CommentController {
    private final CreateCommentUseCase createCommentUseCase;
    private final GetCommentUseCase getCommentUseCase;
    private final GetCommentsByPostIdUseCase getCommentsByPostIdUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final CommentRestMapper mapper;

    @Operation(summary = "댓글 생성")
    @PostMapping
    public ResponseEntity<IdResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CreateCommentRequest request) {
        val command = mapper.toCreateCommand(postId, request);
        Comment comment = createCommentUseCase.execute(command);
        return ResponseEntity.ok(new IdResponse(comment.getId()));
    }

    @Operation(summary = "댓글 조회")
    @GetMapping("/{commentId}")
    public ResponseEntity<GetCommentResponse> getComment(@PathVariable Long commentId) {
        Comment comment = getCommentUseCase.execute(commentId);
        val response = mapper.toGetCommentResponse(comment);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글의 전체 댓글 조회")
    @GetMapping
    public ResponseEntity<List<GetCommentResponse>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = getCommentsByPostIdUseCase.execute(postId);
        val response = mapper.toGetCommentListResponse(comments);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "댓글 수정")
    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request) {
        val command = mapper.toUpdateCommand(commentId, request);
        updateCommentUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        deleteCommentUseCase.execute(commentId);
        return ResponseEntity.noContent().build();
    }
}
