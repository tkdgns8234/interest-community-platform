package com.findy.boundedcontext.topic.app.interfaces;

import com.findy.boundedcontext.topic.domain.model.post.TopicPost;

import java.util.List;
import java.util.Optional;

public interface TopicPostRepository {
    TopicPost save(TopicPost post);
    Optional<TopicPost> findById(Long id);
    void deleteById(Long id);
    List<TopicPost> findByTopicId(Long topicId, Long cursor, int size);
    List<TopicPost> findByAuthorId(Long authorId, Long cursor, int size);
}
