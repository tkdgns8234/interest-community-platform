package com.findy.topic.out.repository.entity;

import com.findy.common.out.repository.entity.BaseTimeEntity;
import com.findy.topic.domain.model.topic.MembershipManager;
import com.findy.topic.domain.model.topic.Topic;
import com.findy.topic.domain.model.topic.TopicInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class TopicEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private Long creatorId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 5000)
    private String introduction;

    @Column(length = 500)
    private String coverImageUrl;

    @Column(nullable = false)
    private Long memberCount;

    public TopicEntity(Topic topic) {
        this.id = topic.getId();
        this.categoryId = topic.getCategoryId();
        this.creatorId = topic.getCreatorId();
        this.name = topic.getName();
        this.introduction = topic.getIntroduction();
        this.coverImageUrl = topic.getCoverImageUrl();
        this.memberCount = topic.getMemberCount();
    }

    public Topic toTopic() {
        return new Topic(
                this.id,
                this.categoryId,
                this.creatorId,
                new TopicInfo(this.name, this.introduction, this.coverImageUrl),
                new MembershipManager(this.memberCount)
        );
    }
}
