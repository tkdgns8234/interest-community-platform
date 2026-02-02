package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.exception.TopicPostNotFoundException;
import com.findy.boundedcontext.topic.app.interfaces.TopicPostRepository;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetTopicPostUseCase {
    private final TopicPostRepository topicPostRepository;

    @Transactional(readOnly = true)
    public TopicPost execute(Long postId) {
        return topicPostRepository.findById(postId)
                .orElseThrow(() -> new TopicPostNotFoundException(postId));
    }
}
