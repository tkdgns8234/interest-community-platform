package com.findy.topic.out.repository.jpa;

import com.findy.topic.out.repository.entity.TopicPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTopicPostRepository extends JpaRepository<TopicPostEntity, Long> {
}
