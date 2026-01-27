package com.findy.post.in.rest;

import com.findy.post.app.LikeService;
import com.findy.post.domain.model.like.TargetType;
import com.findy.post.in.rest.request.LikeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ApiV1LikeController {
    private final LikeService likeService;

    @Operation(summary = "게시글 좋아요")
    @PostMapping("/post/{postId}/like")
    public ResponseEntity<Void> likePost(
            @PathVariable Long postId,
            @RequestBody LikeRequest request) {
        likeService.addLike(request.userId(), postId, TargetType.POST);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시글 좋아요 취소")
    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity<Void> unlikePost(
            @PathVariable Long postId,
            @RequestBody LikeRequest request) {
        likeService.removeLike(request.userId(), postId, TargetType.POST);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "댓글 좋아요")
    @PostMapping("/post/{postId}/comment/{commentId}/like")
    public ResponseEntity<Void> likeComment(
            @PathVariable Long commentId,
            @RequestBody LikeRequest request) {
        likeService.addLike(request.userId(), commentId, TargetType.COMMENT);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "댓글 좋아요 취소")
    @DeleteMapping("/post/{postId}/comment/{commentId}/like")
    public ResponseEntity<Void> unlikeComment(
            @PathVariable Long commentId,
            @RequestBody LikeRequest request) {
        likeService.removeLike(request.userId(), commentId, TargetType.COMMENT);
        return ResponseEntity.noContent().build();
    }
}
