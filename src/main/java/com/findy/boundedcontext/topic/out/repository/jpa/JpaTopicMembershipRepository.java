package com.findy.boundedcontext.topic.out.repository.jpa;

import com.findy.boundedcontext.topic.out.repository.entity.TopicMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTopicMembershipRepository extends JpaRepository<TopicMembershipEntity, Long> {
    Optional<TopicMembershipEntity> findByUserIdAndTopicId(Long userId, Long topicId);
    boolean existsByUserIdAndTopicId(Long userId, Long topicId);
    void deleteByUserIdAndTopicId(Long userId, Long topicId);
    long countByTopicId(Long topicId);
}