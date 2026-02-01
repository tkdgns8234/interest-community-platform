package com.findy.boundedcontext.topic.out.repository.jpa;

import com.findy.boundedcontext.topic.out.repository.entity.TopicPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTopicPostRepository extends JpaRepository<TopicPostEntity, Long> {
}
