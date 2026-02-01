package com.findy.topic.domain.model.post;

import com.findy.post.domain.model.LikeManager;
import com.findy.post.domain.model.post.PostInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TopicPost {
    private final Long id;
    private final Long topicId;
    private final Long authorId;
    private final PostInfo postInfo;
    private final LikeManager likeManager;

    public TopicPost(Long id, Long topicId, Long authorId, PostInfo postInfo) {
        this(id, topicId, authorId, postInfo, new LikeManager());
    }

    @Builder
    public TopicPost(Long id, Long topicId, Long authorId, PostInfo postInfo, LikeManager likeManager) {
        this.id = id;
        this.topicId = topicId;
        this.authorId = authorId;
        this.postInfo = postInfo;
        this.likeManager = likeManager;
    }

    public void updateTitle(String title) {
        this.postInfo.setTitle(title);
    }

    public void updateContent(String content) {
        this.postInfo.setContent(content);
    }

    public boolean isAuthor(Long userId) {
        return this.authorId.equals(userId);
    }

    public void like(Long userId) {
        this.likeManager.like(authorId, userId);
    }

    public void unlike(Long userId) {
        this.likeManager.unlike(authorId, userId);
    }

    public String getTitle() {
        return postInfo.getTitle();
    }

    public String getContent() {
        return postInfo.getContent();
    }

    public long getLikeCount() {
        return likeManager.getCount();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TopicPost topicPost = (TopicPost) o;
        return Objects.equals(id, topicPost.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
