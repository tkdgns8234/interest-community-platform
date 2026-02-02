package com.findy.boundedcontext.post.app.usecase;

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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetPostUseCaseTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private GetPostUseCase getPostUseCase;

    private Post testPost;

    @BeforeEach
    void setUp() {
        PostInfo postInfo = new PostInfo("테스트 제목", "테스트 내용");
        testPost = new Post(1L, 1L, postInfo);
    }

    @Test
    @DisplayName("ID로 게시글을 조회할 수 있다")
    void getPost() {
        given(postRepository.findById(1L)).willReturn(testPost);

        Post post = getPostUseCase.execute(1L);

        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getPostInfo().getTitle()).isEqualTo("테스트 제목");
    }
}
