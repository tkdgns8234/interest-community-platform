package com.findy.boundedcontext.topic.out.repository;

import com.findy.boundedcontext.topic.app.exception.TopicPostNotFoundException;
import com.findy.boundedcontext.topic.app.interfaces.TopicPostRepository;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import com.findy.boundedcontext.topic.out.repository.entity.TopicPostEntity;
import com.findy.boundedcontext.topic.out.repository.jpa.JpaTopicPostRepository;
import com.findy.boundedcontext.topic.out.repository.querydsl.TopicPostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TopicPostRepositoryImpl implements TopicPostRepository {
    private final JpaTopicPostRepository jpaTopicPostRepository;
    private final TopicPostQueryRepository topicPostQueryRepository;

    @Override
    public TopicPost save(TopicPost post) {
        TopicPostEntity entity = new TopicPostEntity(post);
        entity = jpaTopicPostRepository.save(entity);
        return entity.toTopicPost();
    }

    @Override
    public Optional<TopicPost> findById(Long id) {
        return jpaTopicPostRepository.findById(id)
                .map(TopicPostEntity::toTopicPost);
    }

    @Override
    public void deleteById(Long id) {
        if (!jpaTopicPostRepository.existsById(id)) {
            throw new TopicPostNotFoundException(id);
        }
        jpaTopicPostRepository.deleteById(id);
    }

    @Override
    public List<TopicPost> findByTopicId(Long topicId, Long cursor, int size) {
        return topicPostQueryRepository.findByTopicId(topicId, cursor, size).stream()
                .map(TopicPostEntity::toTopicPost)
                .toList();
    }

    @Override
    public List<TopicPost> findByAuthorId(Long authorId, Long cursor, int size) {
        return topicPostQueryRepository.findByAuthorId(authorId, cursor, size).stream()
                .map(TopicPostEntity::toTopicPost)
                .toList();
    }
}
