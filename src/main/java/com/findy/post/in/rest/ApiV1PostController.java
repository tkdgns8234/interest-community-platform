package com.findy.post.in.rest;

import com.findy.common.dto.CursorPageResponse;
import com.findy.common.dto.IdResponse;
import com.findy.post.app.PostService;
import com.findy.post.domain.model.post.Post;
import com.findy.post.in.rest.mapper.PostRestMapper;
import com.findy.post.in.rest.request.CreatePostRequest;
import com.findy.post.in.rest.request.UpdatePostRequest;
import com.findy.post.in.rest.response.GetPostResponse;
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

@Tag(name = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class ApiV1PostController {
    private final PostService postService;
    private final PostRestMapper mapper;

    @Operation(summary = "게시글 생성")
    @PostMapping
    public ResponseEntity<IdResponse> createPost(@RequestBody CreatePostRequest request) {
        val command = mapper.toCreateCommand(request);
        Post post = postService.createPost(command);
        return ResponseEntity.ok(new IdResponse(post.getId()));
    }

    @Operation(summary = "게시글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        val response = mapper.toGetPostResponse(post);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "전체 게시글 목록 조회")
    @GetMapping
    public ResponseEntity<CursorPageResponse<GetPostResponse>> getAllPosts(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size) {
        List<Post> posts = postService.getAllPosts(cursor, size);
        val response = mapper.toGetPostPageResponse(posts, size);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 수정")
    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request) {
        val command = mapper.toUpdateCommand(postId, request);
        postService.updatePost(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
