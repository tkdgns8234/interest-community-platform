package com.findy.boundedcontext.topic.app.interfaces;

import com.findy.boundedcontext.topic.domain.model.topic.Topic;

import java.util.List;

public interface TopicRepository {
    Topic save(Topic topic);
    Topic findById(Long id);
    void deleteById(Long id);
    List<Topic> findAll(Long cursor, int size);
    List<Topic> findByCategoryId(Long categoryId, Long cursor, int size);
}
