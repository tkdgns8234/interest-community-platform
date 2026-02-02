package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.interfaces.TopicPostRepository;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTopicPostsByAuthorIdUseCase {
    private final TopicPostRepository topicPostRepository;

    @Transactional(readOnly = true)
    public List<TopicPost> execute(Long authorId, Long cursor, int size) {
        return topicPostRepository.findByAuthorId(authorId, cursor, size);
    }
}
