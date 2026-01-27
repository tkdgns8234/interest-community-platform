package com.findy.post.in.rest.mapper;

import com.findy.common.dto.CursorPageResponse;
import com.findy.post.app.dto.CreatePostCommand;
import com.findy.post.app.dto.UpdatePostCommand;
import com.findy.post.domain.model.post.Post;
import com.findy.post.in.rest.request.CreatePostRequest;
import com.findy.post.in.rest.request.UpdatePostRequest;
import com.findy.post.in.rest.response.GetPostResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostRestMapper {
    public CreatePostCommand toCreateCommand(CreatePostRequest request) {
        return new CreatePostCommand(
                request.authorId(),
                request.title(),
                request.content()
        );
    }

    public UpdatePostCommand toUpdateCommand(Long id, UpdatePostRequest request) {
        return new UpdatePostCommand(
                id,
                request.title(),
                request.content()
        );
    }

    public GetPostResponse toGetPostResponse(Post post) {
        return new GetPostResponse(
                post.getId(),
                post.getAuthorId(),
                post.getPostInfo().getTitle(),
                post.getPostInfo().getContent(),
                post.getLikeManager().getCount()
        );
    }

    public CursorPageResponse<GetPostResponse> toGetPostPageResponse(List<Post> posts, int size) {
        List<GetPostResponse> responses = posts.stream()
                .map(this::toGetPostResponse)
                .toList();

        return CursorPageResponse.of(responses, size);
    }
}
