package com.findy.post.domain.model.post;

import com.findy.post.domain.exception.ContentValidationException;
import com.findy.post.domain.exception.TitleValidateException;
import lombok.Getter;

@Getter
public class PostInfo {
    private String title;
    private String content;

    public PostInfo(String title, String content) {
        setTitle(title);
        setContent(content);
    }

    public void setTitle(String title) {
        Validator.title(title);
        this.title = title;
    }

    public void setContent(String content) {
        Validator.content(content);
        this.content = content;
    }

    private static class Validator {
        private static final int TITLE_MAX_LENGTH = 255;
        private static final int CONTENT_MAX_LENGTH = 10000;

        public static void title(String title) {
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
