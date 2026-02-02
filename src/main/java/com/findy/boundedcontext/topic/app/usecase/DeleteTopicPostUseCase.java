package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.exception.TopicPostNotFoundException;
import com.findy.boundedcontext.topic.app.exception.UnauthorizedTopicPostAccessException;
import com.findy.boundedcontext.topic.app.interfaces.TopicPostRepository;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteTopicPostUseCase {
    private final TopicPostRepository topicPostRepository;

    @Transactional
    public void execute(Long postId, Long userId) {
        TopicPost post = topicPostRepository.findById(postId)
                .orElseThrow(() -> new TopicPostNotFoundException(postId));

        // 작성자 확인
        if (!post.isAuthor(userId)) {
            throw new UnauthorizedTopicPostAccessException();
        }

        topicPostRepository.deleteById(postId);
    }
}
