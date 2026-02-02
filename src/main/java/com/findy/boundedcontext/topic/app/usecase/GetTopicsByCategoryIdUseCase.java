package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import com.findy.boundedcontext.topic.out.interfaces.CategoryEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTopicsByCategoryIdUseCase {
    private final TopicRepository topicRepository;
    private final CategoryEntryPoint categoryEntryPoint;

    @Transactional(readOnly = true)
    public List<Topic> execute(Long categoryId, Long cursor, int size) {
        // 카테고리 존재 확인
        categoryEntryPoint.validateCategoryExists(categoryId);
        return topicRepository.findByCategoryId(categoryId, cursor, size);
    }
}
