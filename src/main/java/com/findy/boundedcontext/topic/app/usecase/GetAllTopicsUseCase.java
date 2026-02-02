package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllTopicsUseCase {
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<Topic> execute(Long cursor, int size) {
        return topicRepository.findAll(cursor, size);
    }
}
