package com.findy.boundedcontext.post.app.usecase;

import com.findy.global.event.EventPublisher;
import com.findy.boundedcontext.post.app.dto.CreatePostCommand;
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

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private CreatePostUseCase createPostUseCase;

    private Post testPost;

    @BeforeEach
    void setUp() {
        PostInfo postInfo = new PostInfo("테스트 제목", "테스트 내용");
        testPost = new Post(1L, 1L, postInfo);
    }

    @Test
    @DisplayName("게시글을 생성할 수 있다")
    void createPost() {
        given(postRepository.save(any(Post.class))).willReturn(testPost);

        CreatePostCommand command = new CreatePostCommand(1L, "테스트 제목", "테스트 내용");
        Post createdPost = createPostUseCase.execute(command);

        assertThat(createdPost).isNotNull();
        assertThat(createdPost.getId()).isEqualTo(1L);
        assertThat(createdPost.getPostInfo().getTitle()).isEqualTo("테스트 제목");
        assertThat(createdPost.getPostInfo().getContent()).isEqualTo("테스트 내용");
    }
}
