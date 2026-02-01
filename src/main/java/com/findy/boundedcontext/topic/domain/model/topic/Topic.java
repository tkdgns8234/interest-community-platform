package com.findy.boundedcontext.topic.domain.model.topic;

import lombok.Getter;

@Getter
public class Topic {
    private final Long id;
    private final Long categoryId;
    private final Long creatorId;
    private final TopicInfo topicInfo;
    private final MembershipManager membershipManager;

    public Topic(Long id, Long categoryId, Long creatorId, TopicInfo topicInfo) {
        this(id, categoryId, creatorId, topicInfo, new MembershipManager());
    }

    public Topic(Long id, Long categoryId, Long creatorId, TopicInfo topicInfo, MembershipManager membershipManager) {
        this.id = id;
        this.categoryId = categoryId;
        this.creatorId = creatorId;
        this.topicInfo = topicInfo;
        this.membershipManager = membershipManager;
    }

    public void updateInfo(String name, String introduction, String coverImageUrl) {
        if (name != null) {
            this.topicInfo.updateName(name);
        }
        if (introduction != null) {
            this.topicInfo.updateIntroduction(introduction);
        }
        if (coverImageUrl != null) {
            this.topicInfo.updateCoverImageUrl(coverImageUrl);
        }
    }

    public boolean isCreator(Long userId) {
        return this.creatorId.equals(userId);
    }

    public String getName() {
        return topicInfo.getName();
    }

    public String getIntroduction() {
        return topicInfo.getIntroduction();
    }

    public String getCoverImageUrl() {
        return topicInfo.getCoverImageUrl();
    }

    public long getMemberCount() {
        return membershipManager.getMemberCount();
    }
}
