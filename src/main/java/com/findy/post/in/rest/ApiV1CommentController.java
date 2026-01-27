package com.findy.post.in.rest;

import com.findy.common.dto.IdResponse;
import com.findy.post.app.CommentService;
import com.findy.post.domain.model.comment.Comment;
import com.findy.post.in.rest.mapper.CommentRestMapper;
import com.findy.post.in.rest.request.CreateCommentRequest;
import com.findy.post.in.rest.request.UpdateCommentRequest;
import com.findy.post.in.rest.response.GetCommentResponse;
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
    private final CommentService commentService;
    private final CommentRestMapper mapper;

    @Operation(summary = "댓글 생성")
    @PostMapping
    public ResponseEntity<IdResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CreateCommentRequest request) {
        val command = mapper.toCreateCommand(postId, request);
        Comment comment = commentService.createComment(command);
        return ResponseEntity.ok(new IdResponse(comment.getId()));
    }

    @Operation(summary = "댓글 조회")
    @GetMapping("/{commentId}")
    public ResponseEntity<GetCommentResponse> getComment(@PathVariable Long commentId) {
        Comment comment = commentService.getComment(commentId);
        val response = mapper.toGetCommentResponse(comment);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글의 전체 댓글 조회")
    @GetMapping
    public ResponseEntity<List<GetCommentResponse>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        val response = mapper.toGetCommentListResponse(comments);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "댓글 수정")
    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request) {
        val command = mapper.toUpdateCommand(commentId, request);
        commentService.updateComment(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
