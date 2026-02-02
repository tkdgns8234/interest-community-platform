package com.findy.boundedcontext.topic.domain.model.topic;

import com.findy.boundedcontext.topic.domain.exception.InvalidTopicInfoException;
import lombok.Getter;

@Getter
public class TopicInfo {
    private final String name;
    private final String introduction;
    private final String coverImageUrl;

    public TopicInfo(String name, String introduction, String coverImageUrl) {
        Validator.name(name);
        Validator.introduction(introduction);

        this.name = name;
        this.introduction = introduction;
        this.coverImageUrl = coverImageUrl;
    }

    public TopicInfo withName(String name) {
        Validator.name(name);
        return new TopicInfo(name, this.introduction, this.coverImageUrl);
    }

    public TopicInfo withIntroduction(String introduction) {
        Validator.introduction(introduction);
        return new TopicInfo(this.name, introduction, this.coverImageUrl);
    }

    public TopicInfo withCoverImageUrl(String coverImageUrl) {
        return new TopicInfo(this.name, this.introduction, coverImageUrl);
    }

    private static class Validator {
        private static final int NAME_MIN_LENGTH = 2;
        private static final int NAME_MAX_LENGTH = 50;
        private static final int INTRODUCTION_MAX_LENGTH = 5000;

        private static void name(String name) {
            if (name == null || name.isBlank()) {
                throw new InvalidTopicInfoException("Name cannot be null or empty");
            }

            if (name.length() < NAME_MIN_LENGTH) {
                throw new InvalidTopicInfoException("Name must be at least " + NAME_MIN_LENGTH + " characters");
            }

            if (name.length() > NAME_MAX_LENGTH) {
                throw new InvalidTopicInfoException("Name cannot exceed " + NAME_MAX_LENGTH + " characters");
            }
        }

        private static void introduction(String introduction) {
            if (introduction != null && introduction.length() > INTRODUCTION_MAX_LENGTH) {
                throw new InvalidTopicInfoException("Introduction cannot exceed " + INTRODUCTION_MAX_LENGTH + " characters");
            }
        }
    }
}
