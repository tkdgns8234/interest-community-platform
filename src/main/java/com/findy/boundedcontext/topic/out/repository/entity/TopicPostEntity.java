package com.findy.boundedcontext.topic.out.repository.entity;

import com.findy.shared.post.domain.PostInfo;
import com.findy.global.entity.BaseTimeEntity;
import com.findy.boundedcontext.post.domain.model.LikeManager;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "topic_post")
public class TopicPostEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long topicId;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false)
    private Long likeCount;

    public TopicPostEntity(TopicPost post) {
        this.id = post.getId();
        this.topicId = post.getTopicId();
        this.authorId = post.getAuthorId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
    }

    public TopicPost toTopicPost() {
        return TopicPost.builder()
                .id(this.id)
                .topicId(this.topicId)
                .authorId(this.authorId)
                .postInfo(new PostInfo(this.title, this.content))
                .likeManager(new LikeManager(this.likeCount))
                .build();
    }
}
