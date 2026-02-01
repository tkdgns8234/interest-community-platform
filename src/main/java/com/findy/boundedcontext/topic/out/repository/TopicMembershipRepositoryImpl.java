package com.findy.boundedcontext.topic.out.repository;

import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import com.findy.boundedcontext.topic.out.repository.entity.TopicMembershipEntity;
import com.findy.boundedcontext.topic.out.repository.jpa.JpaTopicMembershipRepository;
import com.findy.boundedcontext.topic.out.repository.querydsl.TopicMembershipQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TopicMembershipRepositoryImpl implements TopicMembershipRepository {
    private final JpaTopicMembershipRepository jpaTopicMembershipRepository;
    private final TopicMembershipQueryRepository topicMembershipQueryRepository;

    @Override
    public TopicMembership save(TopicMembership membership) {
        TopicMembershipEntity entity = new TopicMembershipEntity(membership);
        entity = jpaTopicMembershipRepository.save(entity);
        return entity.toTopicMembership();
    }

    @Override
    public Optional<TopicMembership> findById(Long id) {
        return jpaTopicMembershipRepository.findById(id)
                .map(TopicMembershipEntity::toTopicMembership);
    }

    @Override
    public Optional<TopicMembership> findByUserIdAndTopicId(Long userId, Long topicId) {
        return jpaTopicMembershipRepository.findByUserIdAndTopicId(userId, topicId)
                .map(TopicMembershipEntity::toTopicMembership);
    }

    @Override
    public boolean existsByUserIdAndTopicId(Long userId, Long topicId) {
        return jpaTopicMembershipRepository.existsByUserIdAndTopicId(userId, topicId);
    }

    @Override
    public void deleteByUserIdAndTopicId(Long userId, Long topicId) {
        jpaTopicMembershipRepository.deleteByUserIdAndTopicId(userId, topicId);
    }

    @Override
    public List<TopicMembership> findByTopicId(Long topicId, Long cursor, int size) {
        return topicMembershipQueryRepository.findByTopicId(topicId, cursor, size).stream()
                .map(TopicMembershipEntity::toTopicMembership)
                .toList();
    }

    @Override
    public List<TopicMembership> findByUserId(Long userId, Long cursor, int size) {
        return topicMembershipQueryRepository.findByUserId(userId, cursor, size).stream()
                .map(TopicMembershipEntity::toTopicMembership)
                .toList();
    }

    @Override
    public long countByTopicId(Long topicId) {
        return jpaTopicMembershipRepository.countByTopicId(topicId);
    }
}