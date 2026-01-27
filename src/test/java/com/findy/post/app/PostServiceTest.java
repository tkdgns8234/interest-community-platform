package com.findy.post.app;

import com.findy.post.app.dto.CreatePostCommand;
import com.findy.post.app.dto.UpdatePostCommand;
import com.findy.post.app.interfaces.PostRepository;
import com.findy.post.domain.model.post.Post;
import com.findy.post.domain.model.post.PostInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post testPost;

    @BeforeEach
    void setUp() {
        PostInfo postInfo = new PostInfo("테스트 제목", "테스트 내용");
        testPost = new Post(1L, 1L, postInfo);
    }

    @Nested
    @DisplayName("게시글 생성")
    class CreatePost {

        @Test
        @DisplayName("게시글을 생성할 수 있다")
        void createPost() {
            given(postRepository.save(any(Post.class))).willReturn(testPost);

            CreatePostCommand command = new CreatePostCommand(1L, "테스트 제목", "테스트 내용");
            Post createdPost = postService.createPost(command);

            assertThat(createdPost).isNotNull();
            assertThat(createdPost.getId()).isEqualTo(1L);
            assertThat(createdPost.getPostInfo().getTitle()).isEqualTo("테스트 제목");
            assertThat(createdPost.getPostInfo().getContent()).isEqualTo("테스트 내용");
        }
    }

    @Nested
    @DisplayName("게시글 조회")
    class GetPost {

        @Test
        @DisplayName("ID로 게시글을 조회할 수 있다")
        void getPost() {
            given(postRepository.findById(1L)).willReturn(testPost);

            Post post = postService.getPost(1L);

            assertThat(post).isNotNull();
            assertThat(post.getId()).isEqualTo(1L);
            assertThat(post.getPostInfo().getTitle()).isEqualTo("테스트 제목");
        }

        @Test
        @DisplayName("전체 게시글을 조회할 수 있다")
        void getAllPosts() {
            given(postRepository.findAll(null, 20)).willReturn(List.of(testPost));

            List<Post> posts = postService.getAllPosts(null, 20);

            assertThat(posts).hasSize(1);
            assertThat(posts.get(0).getId()).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class UpdatePost {

        @Test
        @DisplayName("게시글을 수정할 수 있다")
        void updatePost() {
            given(postRepository.findById(1L)).willReturn(testPost);
            given(postRepository.save(any(Post.class))).willReturn(testPost);

            UpdatePostCommand command = new UpdatePostCommand(1L, "수정된 제목", "수정된 내용");
            Post updatedPost = postService.updatePost(command);

            assertThat(updatedPost).isNotNull();
            verify(postRepository).save(any(Post.class));
        }
    }

    @Nested
    @DisplayName("게시글 삭제")
    class DeletePost {

        @Test
        @DisplayName("게시글을 삭제할 수 있다")
        void deletePost() {
            postService.deletePost(1L);

            verify(postRepository).deleteById(1L);
        }
    }
}
