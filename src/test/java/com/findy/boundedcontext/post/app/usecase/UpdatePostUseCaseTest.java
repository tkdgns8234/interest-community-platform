package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.dto.UpdatePostCommand;
import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import com.findy.boundedcontext.post.domain.model.post.Post;
import com.findy.boundedcontext.post.domain.model.post.PostInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdatePostUseCaseTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private UpdatePostUseCase updatePostUseCase;

    private Post testPost;

    @BeforeEach
    void setUp() {
        PostInfo postInfo = new PostInfo("테스트 제목", "테스트 내용");
        testPost = new Post(1L, 1L, postInfo);
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다")
    void updatePost() {
        given(postRepository.findById(1L)).willReturn(testPost);
        given(postRepository.save(any(Post.class))).willReturn(testPost);

        UpdatePostCommand command = new UpdatePostCommand(1L, "수정된 제목", "수정된 내용");
        Post updatedPost = updatePostUseCase.execute(command);

        assertThat(updatedPost).isNotNull();
        verify(postRepository).save(any(Post.class));
    }
}
