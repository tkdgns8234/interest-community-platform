package com.findy.shared.post.domain;

import com.findy.boundedcontext.post.domain.exception.ContentValidationException;
import com.findy.boundedcontext.post.domain.exception.TitleValidateException;
import lombok.Getter;

@Getter
public class PostInfo {
    private final String title;
    private final String content;

    public PostInfo(String title, String content) {
        Validator.title(title);
        Validator.content(content);
        this.title = title;
        this.content = content;
    }

    public PostInfo withTitle(String title) {
        Validator.title(title);
        return new PostInfo(title, this.content);
    }

    public PostInfo withContent(String content) {
        Validator.content(content);
        return new PostInfo(this.title, content);
    }

    private static class Validator {
        private static final int TITLE_MAX_LENGTH = 255;
        private static final int CONTENT_MAX_LENGTH = 10000;

        private static void title(String title) {
            if (title.length() > TITLE_MAX_LENGTH) {
                throw new TitleValidateException("Title length exceeds the maximum limit of 255 characters.");
            }
            if (title.trim().isEmpty()) {
                throw new TitleValidateException("Title cannot be empty or whitespace.");
            }
        }

        private static void content(String content) {
            if (content.length() > CONTENT_MAX_LENGTH) {
                throw new ContentValidationException("Content length exceeds the maximum limit of 10000 characters.");
            }
        }
    }
}
