package com.findy.boundedcontext.topic.out.repository;

import com.findy.boundedcontext.topic.app.exception.TopicNotFoundException;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import com.findy.boundedcontext.topic.out.repository.entity.TopicEntity;
import com.findy.boundedcontext.topic.out.repository.jpa.JpaTopicRepository;
import com.findy.boundedcontext.topic.out.repository.querydsl.TopicQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TopicRepositoryImpl implements TopicRepository {
    private final JpaTopicRepository jpaTopicRepository;
    private final TopicQueryRepository topicQueryRepository;

    @Override
    public Topic save(Topic topic) {
        TopicEntity topicEntity = new TopicEntity(topic);
        topicEntity = jpaTopicRepository.save(topicEntity);
        return topicEntity.toTopic();
    }

    @Override
    public Topic findById(Long id) {
        return jpaTopicRepository.findById(id)
                .map(TopicEntity::toTopic)
                .orElseThrow(() -> new TopicNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaTopicRepository.existsById(id)) {
            throw new TopicNotFoundException(id);
        }
        jpaTopicRepository.deleteById(id);
    }

    @Override
    public List<Topic> findAll(Long cursor, int size) {
        return topicQueryRepository.findAll(cursor, size).stream()
                .map(TopicEntity::toTopic)
                .toList();
    }

    @Override
    public List<Topic> findByCategoryId(Long categoryId, Long cursor, int size) {
        return topicQueryRepository.findByCategoryId(categoryId, cursor, size).stream()
                .map(TopicEntity::toTopic)
                .toList();
    }
}
