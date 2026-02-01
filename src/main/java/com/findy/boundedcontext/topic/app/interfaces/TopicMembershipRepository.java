package com.findy.boundedcontext.topic.app.interfaces;

import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;

import java.util.List;
import java.util.Optional;

public interface TopicMembershipRepository {
    TopicMembership save(TopicMembership membership);
    Optional<TopicMembership> findById(Long id);
    Optional<TopicMembership> findByUserIdAndTopicId(Long userId, Long topicId);
    boolean existsByUserIdAndTopicId(Long userId, Long topicId);
    void deleteByUserIdAndTopicId(Long userId, Long topicId);
    List<TopicMembership> findByTopicId(Long topicId, Long cursor, int size);
    List<TopicMembership> findByUserId(Long userId, Long cursor, int size);
    long countByTopicId(Long topicId);
}
