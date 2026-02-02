package com.findy.boundedcontext.post.app.usecase;

import com.findy.global.event.EventPublisher;
import com.findy.boundedcontext.post.app.dto.CreateCommentCommand;
import com.findy.boundedcontext.post.app.interfaces.CommentRepository;
import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import com.findy.boundedcontext.post.domain.model.comment.Comment;
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
class CreateCommentUseCaseTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private CreateCommentUseCase createCommentUseCase;

    private Comment testComment;
    private Post testPost;

    @BeforeEach
    void setUp() {
        testComment = new Comment(1L, 1L, 1L, "테스트 댓글");
        PostInfo postInfo = new PostInfo("테스트 제목", "테스트 내용");
        testPost = new Post(1L, 1L, postInfo);
    }

    @Test
    @DisplayName("댓글을 생성할 수 있다")
    void createComment() {
        given(commentRepository.save(any(Comment.class))).willReturn(testComment);
        given(postRepository.findById(1L)).willReturn(testPost);

        CreateCommentCommand command = new CreateCommentCommand(1L, 1L, "테스트 댓글");
        Comment createdComment = createCommentUseCase.execute(command);

        assertThat(createdComment).isNotNull();
        assertThat(createdComment.getId()).isEqualTo(1L);
        assertThat(createdComment.getContent()).isEqualTo("테스트 댓글");
    }
}
