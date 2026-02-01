package com.findy.boundedcontext.topic.out.repository.jpa;

import com.findy.boundedcontext.topic.out.repository.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTopicRepository extends JpaRepository<TopicEntity, Long> {
}
