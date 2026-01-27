package com.findy.post.in.rest.mapper;

import com.findy.post.app.dto.CreateCommentCommand;
import com.findy.post.app.dto.UpdateCommentCommand;
import com.findy.post.domain.model.comment.Comment;
import com.findy.post.in.rest.request.CreateCommentRequest;
import com.findy.post.in.rest.request.UpdateCommentRequest;
import com.findy.post.in.rest.response.GetCommentResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentRestMapper {
    public CreateCommentCommand toCreateCommand(Long postId, CreateCommentRequest request) {
        return new CreateCommentCommand(
                postId,
                request.authorId(),
                request.content()
        );
    }

    public UpdateCommentCommand toUpdateCommand(Long id, UpdateCommentRequest request) {
        return new UpdateCommentCommand(
                id,
                request.content()
        );
    }

    public GetCommentResponse toGetCommentResponse(Comment comment) {
        return new GetCommentResponse(
                comment.getId(),
                comment.getPostId(),
                comment.getAuthorId(),
                comment.getContent(),
                comment.getIsEdit(),
                comment.getLikeCount()
        );
    }

    public List<GetCommentResponse> toGetCommentListResponse(List<Comment> comments) {
        return comments.stream()
                .map(this::toGetCommentResponse)
                .toList();
    }
}
